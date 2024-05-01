package com.kubiki.test.controller;

import com.kubiki.test.dto.SetSleepDto;
import com.kubiki.test.dto.SetThreadsDto;
import com.kubiki.test.service.LimitedThreadsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/limited-threads")
@AllArgsConstructor
public class LimitedThreadsController {

    private final LimitedThreadsService limitedThreadsService;

    @GetMapping("compute")
    public void compute() {
        limitedThreadsService.compute();
    }

    @PostMapping("set-threads")
    public void setThreads(@RequestBody SetThreadsDto setThreadsDto) {
        limitedThreadsService.setThreadsNum(setThreadsDto.getThreads());
    }

    @PostMapping("set-sleep")
    public void setSleep(@RequestBody SetSleepDto setSleepDto) {
        limitedThreadsService.setSleepTime(setSleepDto.getSleep());
    }

}
