package com.example.droolsprototype;

import com.kubiki.controller.commons.actions.dtos.NumberAndDelayActionArgs;
import com.kubiki.controller.commons.definitons.ActionScheduleRequest;
import com.kubiki.controller.sample.actions.NumberAndDelayAction;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumberAndDelayActionFactory {

    public static Stream<ActionScheduleRequest> getActions(int count, int priority, int delay, long window) {
        return IntStream.range(0, count)
                .mapToObj(i -> new NumberAndDelayAction(new NumberAndDelayActionArgs(0), window))
                .map(a -> new ActionScheduleRequest(a, "idempotencyKey", priority, delay));
    }
}
