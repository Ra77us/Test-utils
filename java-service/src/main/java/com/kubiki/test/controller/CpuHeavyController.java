package com.kubiki.test.controller;

import com.kubiki.test.service.CpuHeavyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cpu")
@AllArgsConstructor
public class CpuHeavyController {

    private final CpuHeavyService cpuHeavyService;

    @GetMapping("compute")
    public int compute() {
        return cpuHeavyService.compute();
    }
}
