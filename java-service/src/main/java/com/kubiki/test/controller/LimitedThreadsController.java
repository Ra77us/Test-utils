package com.kubiki.test.controller;

import com.kubiki.test.dto.SetThreadsDto;
import com.kubiki.test.service.LimitedThreadsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/limited-threads")
@AllArgsConstructor
public class LimitedThreadsController {

    private final LimitedThreadsService limitedThreadsService;

    @GetMapping("compute")
    public int compute() throws ExecutionException, InterruptedException {
        return limitedThreadsService.compute();
    }

    @PostMapping("set-threads")
    public void setThreads(@RequestBody SetThreadsDto setThreadsDto) {
        limitedThreadsService.setThreadsNum(setThreadsDto.getThreads());
    }

}
