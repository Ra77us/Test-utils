package com.kubiki.test.controller;

import com.kubiki.test.service.ConfigService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ManagementController {

    private final ConfigService configService;

    @GetMapping("/start")
    public void startSending(@RequestParam int value) {
        configService.start(value);
    }

    @GetMapping("/stop")
    public void stopSending() {
        configService.stop();
    }
}
