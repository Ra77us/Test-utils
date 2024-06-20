package com.example.droolsprototype.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class DemoTaskRunner {
    private final TimerTask demoTask;
    private final Timer timer = new Timer();

    @Value("${loop.delay}")
    private int delay;

    public DemoTaskRunner(TimerTask demoTask) {
        this.demoTask = demoTask;
    }

    public void run() {
        timer.scheduleAtFixedRate(demoTask, 0, delay);
    }

}
