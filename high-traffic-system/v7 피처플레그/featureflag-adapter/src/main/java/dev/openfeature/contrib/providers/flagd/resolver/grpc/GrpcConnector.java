package dev.openfeature.contrib.providers.flagd.resolver.grpc;

import com.hopoong.featureflag_adapter.openfeatureflag.cache.MyCache;
import dev.openfeature.contrib.providers.flagd.FlagdOptions;
import dev.openfeature.contrib.providers.flagd.resolver.common.ChannelBuilder;
import dev.openfeature.contrib.providers.flagd.resolver.common.Util;
import dev.openfeature.flagd.grpc.Schema;
import dev.openfeature.flagd.grpc.ServiceGrpc;
import dev.openfeature.sdk.ProviderState;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * Class that abstracts the gRPC communication with flagd.
 */
@Slf4j
public class GrpcConnector {
    private final Object sync = new Object();
    private final AtomicBoolean connected = new AtomicBoolean(false);
    private final Random random = new Random();
    private final int maxJitter = 100;

    private final ServiceGrpc.ServiceBlockingStub serviceBlockingStub;
    private final ServiceGrpc.ServiceStub serviceStub;
    private final ManagedChannel channel;
    private final int maxEventStreamRetries;

    private final int startEventStreamRetryBackoff;
    private final long deadline;

    private final MyCache cache;
    private final Consumer<ProviderState> stateConsumer;

    private int eventStreamAttempt = 1;
    private int eventStreamRetryBackoff;

    // Thread responsible for event observation
    private Thread eventObserverThread;

    /**
     * GrpcConnector creates an abstraction over gRPC communication.
     *
     * @param options       options to build the gRPC channel.
     * @param cache         cache to use.
     * @param stateConsumer lambda to call for setting the state.
     */
    public GrpcConnector(final FlagdOptions options, final MyCache cache, Consumer<ProviderState> stateConsumer) {
        this.channel = ChannelBuilder.nettyChannel(options);
        this.serviceStub = ServiceGrpc.newStub(channel);
        this.serviceBlockingStub = ServiceGrpc.newBlockingStub(channel);

        this.maxEventStreamRetries = options.getMaxEventStreamRetries();
        this.startEventStreamRetryBackoff = options.getRetryBackoffMs();
        this.eventStreamRetryBackoff = options.getRetryBackoffMs();
        this.deadline = options.getDeadline();
        this.cache = cache;
        this.stateConsumer = stateConsumer;
    }

    /**
     * Initialize the gRPC stream.
     */
    public void initialize() throws Exception {
        eventObserverThread = new Thread(this::observeEventStream);
        eventObserverThread.setDaemon(true);
        eventObserverThread.start();

        // block till ready
        Util.busyWaitAndCheck(this.deadline, this.connected);
    }

    /**
     * Shuts down all gRPC resources.
     *
     * @throws Exception is something goes wrong while terminating the communication.
     */
    public void shutdown() throws Exception {
        // first shutdown the event listener
        if (this.eventObserverThread != null) {
            this.eventObserverThread.interrupt();
        }

        try {
            if (this.channel != null && !this.channel.isShutdown()) {
                this.channel.shutdown();
                this.channel.awaitTermination(this.deadline, TimeUnit.MILLISECONDS);
            }
        } finally {
            this.cache.clear();
            if (this.channel != null && !this.channel.isShutdown()) {
                this.channel.shutdownNow();
                this.channel.awaitTermination(this.deadline, TimeUnit.MILLISECONDS);
                log.warn(String.format("Unable to shut down channel by %d deadline", this.deadline));
            }
        }
    }

    /**
     * Provide the object that can be used to resolve Feature Flag values.
     *
     * @return a {@link ServiceGrpc.ServiceBlockingStub} for running FF resolution.
     */
    public ServiceGrpc.ServiceBlockingStub getResolver() {
        return serviceBlockingStub.withDeadlineAfter(this.deadline, TimeUnit.MILLISECONDS);
    }

    /**
     * Event stream observer logic. This contains blocking mechanisms, hence must be run in a dedicated thread.
     */
    private void observeEventStream() {
        while (this.eventStreamAttempt <= this.maxEventStreamRetries) {
            final StreamObserver<Schema.EventStreamResponse> responseObserver =
                    new EventStreamObserver(sync, this.cache, this::grpcStateConsumer);
            this.serviceStub.eventStream(Schema.EventStreamRequest.getDefaultInstance(), responseObserver);

            try {
                synchronized (sync) {
                    sync.wait();
                }
            } catch (InterruptedException e) {
                // Interruptions are considered end calls for this observer, hence log and return
                // Note - this is the most common interruption when shutdown, hence the log level debug
                log.debug("interruption while waiting for condition", e);
                return;
            }

            this.eventStreamAttempt++;
            // backoff with a jitter
            this.eventStreamRetryBackoff = 2 * this.eventStreamRetryBackoff + random.nextInt(maxJitter);

            try {
                Thread.sleep(this.eventStreamRetryBackoff);
            } catch (InterruptedException e) {
                // Interruptions are considered end calls for this observer, hence log and return
                log.warn("interrupted while restarting gRPC Event Stream");
                return;
            }
        }

        log.error("failed to connect to event stream, exhausted retries");
        this.grpcStateConsumer(ProviderState.ERROR);
    }

    private void grpcStateConsumer(final ProviderState state) {
        // check for readiness
        if (ProviderState.READY.equals(state)) {
            this.eventStreamAttempt = 1;
            this.eventStreamRetryBackoff = this.startEventStreamRetryBackoff;
            this.connected.set(true);
        } else if (ProviderState.ERROR.equals(state)) {
            // reset connection status
            this.connected.set(false);
        }

        // chain to initiator
        this.stateConsumer.accept(state);
    }
}
