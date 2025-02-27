package com.hopoong.featureflag_adapter;


import com.hopoong.featureflag_adapter.metric.FeatureflagCounter;
import dev.openfeature.contrib.providers.flagd.Config;
import dev.openfeature.contrib.providers.flagd.FlagdOptions;
import dev.openfeature.contrib.providers.flagd.FlagdProvider;
import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.OpenFeatureAPI;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@ComponentScan
@EnableAutoConfiguration
@RequiredArgsConstructor
public class FeatureflagAdapterConfig {

    private final Environment environment;
    private final MeterRegistry registry;

    @Bean
    public Client featureflagAdapter() {
        String flagdHost = environment.getProperty("FEATUREFLAG_ENGINE_HOST") == null ? "localhost" : environment.getProperty("FEATUREFLAG_ENGINE_HOST");
        String flagdPort = environment.getProperty("FEATUREFLAG_ENGINE_PORT") == null ? "8013" : environment.getProperty("FEATUREFLAG_ENGINE_PORT");
        String maxCacheSize = environment.getProperty("MAX_CACHE_SIZE") == null ? "100" : environment.getProperty("MAX_CACHE_SIZE");

        FlagdProvider flagd = new FlagdProvider(
                FlagdOptions.builder()
                        .resolverType(Config.Evaluator.RPC)
                        .host(flagdHost)
                        .port(Integer.parseInt(flagdPort))
                        .tls(false)
                        .maxCacheSize(Integer.parseInt(maxCacheSize))
                        .retryBackoffMs(100)
                        .deadline(3000)
                        .cacheType("lru")
                        .build(),
                featureflagCounter()
        );

        OpenFeatureAPI api = OpenFeatureAPI.getInstance();
        api.setProvider(flagd);
        return api.getClient();
    }


    public FeatureflagCounter featureflagCounter() {
        return FeatureflagCounter.standard(registry);
    }

}
