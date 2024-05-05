package com.kubiki.test.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class ManagementService {

    @Value("${complex.service.path}")
    String url;

    private RestTemplate restTemplate = new RestTemplate();

    public void send(int value) {
        log.info("Send request to external service");
        ResponseEntity<Long> response = restTemplate.getForEntity(url + "/operation/count?value=" + value, Long.class);
        log.info("Response: {}", response);
    }
}
