package com.kubiki.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@RequiredArgsConstructor
@Log4j2
public class LimitedThreadsService {

    private final ExecutorService executorService;
    private final ExecutorService executor;

    //@Value("${test3.url}")
    private String url = "http://149.156.182.229:31022";

    public int compute() throws ExecutionException, InterruptedException {
        log.info("compute");
        CompletableFuture<Integer> res = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return callExternalService();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, executorService);
        return res.get();
    }

    public void setThreadsNum(int threadsNum) {
        ((ThreadPoolExecutor) executor).setCorePoolSize(threadsNum);
    }

    public int callExternalService() throws InterruptedException {
        int res = -1;
        while (res == -1) {
            try {
               res = new RestTemplate().getForObject(url + "/cpu/compute", Integer.class);
            } catch (Exception ex) {
                log.error(ex);
                Thread.sleep(500);
            }
        }
        return res;
    }
}
