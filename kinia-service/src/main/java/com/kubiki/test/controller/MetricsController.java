package com.kubiki.test.controller;

import com.kubiki.test.service.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/test")
public class MetricsController {

    private final MetricsService metricsService;

    @GetMapping("/increment")
    public void incrementMetricValue(@RequestParam(required = false) int value) {
        metricsService.increment(value);
    }

    @GetMapping("/decrement")
    public void decrementMetricValue(@RequestParam(required = false) int value) {
        metricsService.decrement(value);
    }

    @GetMapping("/reset")
    public void resetMetricValue() {
        metricsService.reset();
    }
}
