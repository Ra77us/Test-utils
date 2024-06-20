package com.example.manualinvoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point
 */
@SpringBootApplication(scanBasePackages = {"com.example.manualinvoker", "com.kubiki.controller.commons"})
public class ManualInvokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManualInvokerApplication.class, args);
    }
}
