package com.kubiki.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ExecutorConfig {
    @Bean
    public ExecutorService executor() {
        ExecutorService ex = Executors.newFixedThreadPool(10);
        ((ThreadPoolExecutor) ex).setMaximumPoolSize(10000);
        return ex;
    }
}
