package com.kubiki.test.service;

import com.kubiki.test.dto.SampleDto;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@NoArgsConstructor
@Log4j2
public class ExternalDataService {

    public static String externalDataSourceBaseUrl = "http://localhost:8080/test/";

    public static boolean useSlowPath = true;


    public List<SampleDto> getDataFromExternalSource() {
        String subPath = useSlowPath ? "get-data-slow" : "get-data-fast";
        return new RestTemplate().getForObject(externalDataSourceBaseUrl + subPath, List.class);
    }

    public void setExternalDataSourceBaseUrl(String externalDataSource) {
        log.warn(externalDataSource);
        ExternalDataService.externalDataSourceBaseUrl = externalDataSource;
    }

    public void setUseSlowPath(boolean useSlowPath) {
        log.warn("====>" + useSlowPath);
        ExternalDataService.useSlowPath = useSlowPath;
    }
}
