package com.example.droolsprototype.demo;

import com.kubiki.controller.commons.definitons.ActionBase;
import com.kubiki.controller.commons.definitons.ActionInvoker;
import lombok.AllArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class ExecutorWrapper {
    private ActionInvoker actionInvoker;

    public <T extends ActionBase> void execute(T action) {
        try {
            log.info("Scheduling action: {}", action);
            actionInvoker.invokeViaRabbit(action, "test", 0, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
