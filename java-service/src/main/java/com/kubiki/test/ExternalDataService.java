package com.kubiki.test;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@NoArgsConstructor
@Log4j2
public class ExternalDataService {

    public static String externalDataSource = "http://localhost:8080/test/get-data-slow";


    public List<SampleDto> getDataFromExternalSource() {
        return new RestTemplate().getForObject(externalDataSource, List.class);
    }

    public void setExternalDataSource(String externalDataSource) {
        log.warn(externalDataSource);
        ExternalDataService.externalDataSource = externalDataSource;
    }
}
