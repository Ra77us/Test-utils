package com.example.droolsprototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point
 */
@SpringBootApplication(scanBasePackages = {"com.example.droolsprototype", "com.kubiki.controller.commons"})
public class DroolsPrototypeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DroolsPrototypeApplication.class, args);
    }
}
