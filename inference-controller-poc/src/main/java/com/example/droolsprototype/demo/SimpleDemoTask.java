package com.example.droolsprototype.demo;

import com.example.droolsprototype.services.PrometheusQueryService;
import lombok.AllArgsConstructor;
import org.kie.api.runtime.StatelessKieSession;

import java.util.TimerTask;

@AllArgsConstructor
public abstract class SimpleDemoTask extends TimerTask {

    protected final PrometheusQueryService queryService;
    protected final StatelessKieSession kieSession;

    public abstract void run();
}
