package com.hopoong.featureflag_adapter.metric;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;

/*
 * Prometheus 메트릭에 데이터 넣는 부분.
 */
@AllArgsConstructor
public class FeatureflagCounter {

    private final Counter cacheHitCounter;
    private final Counter cacheMissCounter;
    private final Counter cacheEvictionCounter;

    public static FeatureflagCounter standard(MeterRegistry registry) {
        registry.config().commonTags("app-name", "featureflag-app"); // Prometheus 메트릭에 사용할 태그

        return new FeatureflagCounter(
                buildCacheHitCounter(registry),
                buildCacheMissCounter(registry),
                buildCacheEvictionCounter(registry)
        );
    }

    private static Counter buildCacheHitCounter(MeterRegistry registry) {
        String counterName = "cache_counter.hit";
        return registry.counter(counterName);
    }

    private static Counter buildCacheMissCounter(MeterRegistry registry) {
        String counterName = "cache_counter.miss";
        return registry.counter(counterName);
    }

    private static Counter buildCacheEvictionCounter(MeterRegistry registry) {
        String counterName = "cache_counter.eviction";
        return registry.counter(counterName);
    }

    public void incrementCacheHit() {
        cacheHitCounter.increment();
    }

    public void incrementCacheMiss() {
        cacheMissCounter.increment();
    }

    public void incrementEvictionCount() {
        cacheEvictionCounter.increment();
    }

}
