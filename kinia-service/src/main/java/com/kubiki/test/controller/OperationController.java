package com.kubiki.test.controller;

import com.kubiki.test.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/operation")
public class OperationController {

    private final OperationService operationService;

    @GetMapping("/count")
    public int count(@RequestParam(required = false) int value) {
        Thread thread = new Thread(() -> {
            operationService.padovanSequence(value);
        });
        thread.start();
        return 0;
    }
}
