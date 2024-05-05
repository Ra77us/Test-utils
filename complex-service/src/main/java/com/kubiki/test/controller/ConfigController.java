package com.kubiki.test.controller;

import com.kubiki.test.service.ConfigService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@AllArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @PutMapping("/change")
    public boolean changeFrequency(@RequestParam int frequency) {
        return configService.changeFrequency(frequency);
    }
}
