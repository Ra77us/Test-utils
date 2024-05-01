package com.kubiki.test.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@AllArgsConstructor
@Log4j2
public class LimitedThreadsService {

    private final ExecutorService executorService;
    private final ExecutorService executor;


    private static int sleepTime = 10000;


    public void compute() {
        executorService.execute(() -> sleep(sleepTime));
    }

    public void setSleepTime(int sleepTime) {
        LimitedThreadsService.sleepTime = sleepTime;
    }

    public void setThreadsNum(int threadsNum) {
        ((ThreadPoolExecutor) executor).setCorePoolSize(threadsNum);
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
