package com.example.droolsprototype.demo;

import com.example.droolsprototype.services.PrometheusQueryService;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Demo performing queries and firing mock rule engine rules
 */
@Component
public class DemoTask extends SimpleDemoTask {
    private final StringBuilder logBuilder;
    private final ExecutorWrapper executorWrapper;
    Logger logger = LoggerFactory.getLogger(DemoTask.class);

    public DemoTask(PrometheusQueryService queryService, StatelessKieSession kieSession, ExecutorWrapper executorWrapper) {
        super(queryService, kieSession);
        this.executorWrapper = executorWrapper;
        this.logBuilder = new StringBuilder();
    }

    public void run() {
        // sending requests for metrics
        List<Metric> queryResults = List.of();
        try {
            queryResults = queryService.queryMetrics();
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info(Arrays.toString(e.getStackTrace()));
            logger.info("will try again");
            return;
        }
        if (!queryResults.isEmpty()) {

            // prepare list including results and executor
            List<Object> kieInput = Stream.concat(
                    queryResults.stream(), Stream.of(executorWrapper))
                    .collect(Collectors.toList());

            // tell drools to evaluate all rules if any metric has been added

            logger.info("Running drools...");
            kieSession.execute(kieInput);
        }
    }

    public String getQueryLogs() {
        return logBuilder.toString();
    }
}
