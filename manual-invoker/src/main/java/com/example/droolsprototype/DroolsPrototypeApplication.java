package com.example.droolsprototype;

import com.kubiki.controller.commons.definitons.ActionInvoker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Application entry point
 */
@SpringBootApplication(scanBasePackages = {"com.example.droolsprototype"})
public class DroolsPrototypeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DroolsPrototypeApplication.class, args);
    }
}
