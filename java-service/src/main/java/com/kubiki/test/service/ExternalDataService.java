package com.kubiki.test.service;

import com.kubiki.test.dto.SampleDto;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@NoArgsConstructor
@Log4j2
public class ExternalDataService {

    public static String DATA_PATH = "/test/get-data";

    public static boolean useSlowPath = true;

    @Value("${test2.slow.path}")
    private String slowPath;
    @Value("${test2.fast.path}")
    private String fastPath;

    public List<SampleDto> getDataFromExternalSource() {
        String dataSource = useSlowPath ? slowPath : fastPath;
        log.info("providing data from {}", dataSource + DATA_PATH);
        return new RestTemplate().getForObject(dataSource + DATA_PATH, List.class);
    }

    public void setUseSlowPath(boolean useSlowPath) {
        log.warn("====>" + useSlowPath);
        ExternalDataService.useSlowPath = useSlowPath;
    }
}
