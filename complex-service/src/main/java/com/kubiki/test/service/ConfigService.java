package com.kubiki.test.service;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Log4j2
public class ConfigService {

    @Getter
    private int frequency = 10;
    private Timer timer;
    private final ManagementService managementService;


    public ConfigService(ManagementService managementService) {
        this.timer = new Timer();
        this.managementService = managementService;
    }

    public boolean changeFrequency(int frequency) {
        this.frequency = frequency;
        log.info("Frequency set to " + frequency);
        return true;
    }

    public void start(int value) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                managementService.send(value);
            }
        };
        timer.schedule(task, 0, getFrequency() * 1000L);
        log.info("Started with frequency " + frequency);
    }

    public void stop() {
        timer.cancel();
        log.info("Stopped");
    }
}
