package com.kubiki.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;

@Service
public class MetricsService {

    private final Gauge gauge;

    @Autowired
    public MetricsService(CollectorRegistry registry) {
        gauge = Gauge.build()
                .help("volume usages")
                .name("volume_usages")
                .labelNames("pv")
                .register(registry);
    }

    public void increment(int value) {
        gauge.labels("pv-name").inc(value);
    }

    public void decrement(int value) {
        gauge.labels("pv-name").inc(-value);
    }

    public void reset() {
        gauge.labels("pv-name").set(0);
    }
}
