package com.kubiki.test.controller;

import com.kubiki.test.service.DataService;
import com.kubiki.test.service.ExternalDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utils")
@AllArgsConstructor
public class UtilController {

    private final DataService service;
    private final ExternalDataService externalDataService;

    @PostMapping("set-throttle")
    public void setThrottle(@RequestParam int sharedDelay, @RequestParam int individualDelay, @RequestParam int smallDelay) {
        service.setDelays(sharedDelay, individualDelay, smallDelay);
    }

    @PostMapping("use-new-db")
    public void useSlowPath() {
        externalDataService.setUseSlowPath(true);
    }

    @PostMapping("use-old-db")
    public void useFastPath() {
        externalDataService.setUseSlowPath(false);
    }
}
