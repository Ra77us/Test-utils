package com.kubiki.test.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

@Configuration
@AllArgsConstructor
public class MonitoredExecutorConfig {

    private final ExecutorService executor;

    @Bean
    public ExecutorService executorService(final MeterRegistry registry) {
        return ExecutorServiceMetrics.monitor(registry, executor, "scalable-executor");
    }
}
