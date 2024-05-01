package com.kubiki.test.controller;

import com.kubiki.test.dto.SampleDto;
import com.kubiki.test.service.DataService;
import com.kubiki.test.service.ExternalDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final DataService dataService;
    private final ExternalDataService externalDataService;

    @GetMapping("get-data-slow")
    public List<SampleDto> getListWithThrottling() {
        return dataService.getDataWithThrottling();
    }

    @GetMapping("get-data-fast")
    public List<SampleDto> egtData() {
        return dataService.getData();
    }

    @GetMapping("get-data-external")
    public List<SampleDto> getExternalData() {
        return externalDataService.getDataFromExternalSource();
    }
}
