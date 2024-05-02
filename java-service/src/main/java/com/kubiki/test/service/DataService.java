package com.kubiki.test.service;

import com.kubiki.test.dto.SampleDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class DataService {

    private static final Lock lock = new ReentrantLock();
    private static final List<SampleDto> result = List.of(new SampleDto(1, "alice"), new SampleDto(2, "bob"));
    private static int sharedDelay = 100;
    private static int individualDelay = 300;
    private static int smallDelay = 300;

    @Value("${test2.slow:false}")
    private boolean isSlow;

    public void setDelays(int sharedDelay, int individualDelay, int smallDelay) {
        DataService.sharedDelay = sharedDelay;
        DataService.individualDelay = individualDelay;
        DataService.smallDelay = smallDelay;
    }

    public List<SampleDto> getData() {
        return isSlow ? getDataSlow() : getDataFast();
    }

    public List<SampleDto> getDataSlow() {
        simulateSharedResourceThrottling();
        sleep(individualDelay);
        return result;
    }

    public List<SampleDto> getDataFast() {
        sleep(smallDelay);
        return result;
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void simulateSharedResourceThrottling() {
        lock.lock();
        try {
            log.info("{} longRunningPublish before sleep", Thread.currentThread().threadId());
            Thread.sleep(sharedDelay);
            log.info("{} longRunningPublish after sleep", Thread.currentThread().threadId());
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
        } finally {
            lock.unlock();
        }
    }

}
