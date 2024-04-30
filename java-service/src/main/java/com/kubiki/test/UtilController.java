package com.kubiki.test;

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

    @PostMapping("set-external-database-url")
    public void setThrottle(@RequestParam String url) {
        externalDataService.setExternalDataSourceBaseUrl(url);
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
