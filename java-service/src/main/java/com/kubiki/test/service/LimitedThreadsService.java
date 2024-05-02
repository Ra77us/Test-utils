package com.kubiki.test.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class LimitedThreadsService {

    private ExecutorService executorService;
    private ExecutorService executor;

    @Value("${test3.url}")
    private String url;

    public void compute() {
        executorService.execute(() -> new RestTemplate().getForObject(url, Integer.class));
    }

    public void setThreadsNum(int threadsNum) {
        ((ThreadPoolExecutor) executor).setCorePoolSize(threadsNum);
    }

}
