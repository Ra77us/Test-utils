package com.kubiki.test;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@NoArgsConstructor
public class ExternalDataService {

    public static String externalDataSource = "http://localhost:8080/test/get-data-slow";


    public List<SampleDto> getDataFromExternalSource() {
        return new RestTemplate().getForObject(externalDataSource, List.class);
    }

    public void setExternalDataSource(String externalDataSource) {
        ExternalDataService.externalDataSource = externalDataSource;
    }
}
