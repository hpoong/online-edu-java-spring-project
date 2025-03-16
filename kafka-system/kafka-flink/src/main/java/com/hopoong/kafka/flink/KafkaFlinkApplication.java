package com.hopoong.kafka.flink;

import com.hopoong.kafka.flink.records.ClickEvent;
import com.hopoong.kafka.flink.records.ClickEventDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class KafkaFlinkApplication {


    // option
    public static final String CHECKPOINTING_OPTION = "checkpointing";
    public static final String OPERATOR_CHAINING_OPTION = "chaining";
    public static final String EVENT_TIME_OPTION = "event-time";

    public static final Time WINDOW_SIZE = Time.of(15, TimeUnit.SECONDS);


    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        configureEnvironment(params, env);


        String inputTopic = params.get("input-topic", "input");
        String outputTopic = params.get("output-topic", "output");
        String brokers = params.get("bootstrap.servers", "localhost:9092");

        Properties kafkaProps = new Properties();
        kafkaProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        kafkaProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "click-event-count");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        KafkaSource<ClickEvent> source = KafkaSource.<ClickEvent>builder()
                .setProperties(kafkaProps)
                .setTopics(inputTopic)
                .setValueOnlyDeserializer(new ClickEventDeserializationSchema())
                .setStartingOffsets(OffsetsInitializer.earliest())
                .build();


        // 워터마크
        WatermarkStrategy<ClickEvent> watermarkStrategy = WatermarkStrategy
                .<ClickEvent>forBoundedOutOfOrderness(Duration.ofMillis(200)) // event 최대 지연 시간을 워터마크로 사용
                .withIdleness(Duration.ofSeconds(5))
                .withTimestampAssigner((clickEvent, l) -> clickEvent.getTimestamp().getTime());
    }


    private static void configureEnvironment(ParameterTool params, StreamExecutionEnvironment env) {
        boolean checkpointingEnabled = params.has(CHECKPOINTING_OPTION);
        boolean enableChaining = params.has(OPERATOR_CHAINING_OPTION);

        if(checkpointingEnabled) { // 체크포인트
            env.enableCheckpointing(1000);
        }

        if(!enableChaining) { // 체이닝
            env.disableOperatorChaining();
        }
    }


}