package com.kubiki.test.service;

import com.kubiki.test.dto.SampleDto;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Log4j2
public class ExternalDataService {

    private final MeterRegistry meterRegistry;
    private final Counter counter;

    public static String DATA_PATH = "/test/get-data";

    public static boolean useSlowPath = true;

    public ExternalDataService(MeterRegistry registry) {
        this.meterRegistry = registry;
        this.counter = Counter.builder("external-data-incoming-requests-count")
                .description("a number of requests to /test/get-data-external endpoint")
                .register(meterRegistry);
    }

    @Value("${test2.slow.path}")
    private String slowPath;
    @Value("${test2.fast.path}")
    private String fastPath;

    public List<SampleDto> getDataFromExternalSource() {
        counter.increment();
        String dataSource = useSlowPath ? slowPath : fastPath;
        log.info("providing data from {}", dataSource + DATA_PATH);
        return new RestTemplate().getForObject(dataSource + DATA_PATH, List.class);
    }

    public void setUseSlowPath(boolean useSlowPath) {
        log.warn("====>" + useSlowPath);
        ExternalDataService.useSlowPath = useSlowPath;
    }
}
