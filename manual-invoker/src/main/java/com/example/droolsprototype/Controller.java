package com.example.droolsprototype;

import com.kubiki.controller.commons.actions.dtos.MockRetryActionArgs;
import com.kubiki.controller.commons.actions.dtos.infra.ChangePodCPUActionArgs;
import com.kubiki.controller.commons.definitons.ActionInvoker;
import com.kubiki.controller.commons.definitons.ActionScheduleRequest;
import com.kubiki.controller.sample.actions.ChangeApplicationThreadsNumberAction;
import com.kubiki.controller.sample.actions.FailingAction;
import com.kubiki.controller.sample.actions.MockRetryAction;
import com.kubiki.controller.sample.actions.ScaleHeavyFlowAction;
import com.kubiki.controller.sample.actions.infra.ChangePodCPUAction;
import com.kubiki.controller.sample.dto.ChangeApplicationThreadsNumberArgs;
import com.kubiki.controller.sample.dto.ScaleHeavyFlowActionArgs;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class Controller {

    private final ActionInvoker actionInvoker;

    @GetMapping("test6")
    public void test6() {
        Integer check = new RestTemplate().getForObject("http://149.156.182.229:32222/test/success-counter", Integer.class);
        System.out.println(check);
        for (int retry = 0; retry < 6; retry++) {
            int finalRetry = retry;
            List.of(10, 25, 50, 75).forEach(fail -> {
                ActionScheduleRequest request = new ActionScheduleRequest(new FailingAction(fail, finalRetry));
                actionInvoker.invokeBatchViaRabbit(IntStream.range(0, 1000).mapToObj(i -> request).toList());
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Integer res = new RestTemplate().getForObject("http://149.156.182.229:32222/test/success-counter", Integer.class);
                System.out.println(finalRetry + "," + fail + "," + res);
            });
        }
    }

    @PostMapping("complex")
    public void sendComplexAction() {
        //config
        actionInvoker.invokeAction(
                new ScaleHeavyFlowAction(new ScaleHeavyFlowActionArgs(
                        10, "util-service-1.kubiki:8080", 1, "util-service-2", "kubiki")),
                null);
        sleep(60);
        //test
        actionInvoker.invokeAction(
                new ScaleHeavyFlowAction(new ScaleHeavyFlowActionArgs(
                        20, "util-service-1.kubiki:8080", 2, "util-service-2", "kubiki")),
                null);
        sleep(30);
        actionInvoker.invokeAction(
                new ScaleHeavyFlowAction(new ScaleHeavyFlowActionArgs(
                        25, "util-service-1.kubiki:8080", 1.5, "util-service-2", "kubiki")),
                null);
        sleep(30);
        actionInvoker.invokeAction(
                new ScaleHeavyFlowAction(new ScaleHeavyFlowActionArgs(
                        40, "util-service-1.kubiki:8080", 2, "util-service-2", "kubiki")),
                null);
        sleep(30);
        actionInvoker.invokeAction(
                new ScaleHeavyFlowAction(new ScaleHeavyFlowActionArgs(
                        40, "util-service-1.kubiki:8080", 4, "util-service-2", "kubiki")),
                null);
    }

    @GetMapping("test1")
    public void test1() {
        int n = 50;
        Integer check = new RestTemplate().getForObject("http://149.156.182.229:32222/test/counter", Integer.class);
        System.out.println(check);

        List<ActionScheduleRequest> t1 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, 0, 6))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t1", n, t1, 8);

        List<ActionScheduleRequest> t2 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, (19 - i) / 4, 12))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t2", n, t2, 14);

        List<ActionScheduleRequest> t3 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, (19 - i) / 5, 11))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t3", n, t3, 13);

        List<ActionScheduleRequest> t4 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, (19 - i) / 10, 9))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t4", n, t4, 11);

        List<ActionScheduleRequest> t5 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, (19 - i) / 2, 16))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t5", n, t5, 17);

        List<ActionScheduleRequest> t6 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, 19 - i, 23))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t6", n, t6, 24);
    }

    private Stream<ActionScheduleRequest> getActions(int count, int priority, int delay, long window) {
        return NumberAndDelayActionFactory.getActions(count, priority, delay, window);
    }

    private void performTests(String name, int n, List<ActionScheduleRequest> actions, int sleep) {
        System.out.println(name);
        for (int i=0; i<n; i++) {
            performTest(actions, sleep);
        }
    }

    private void performTest(List<ActionScheduleRequest> actions, int sleep) {
        Collections.shuffle(actions);
        actionInvoker.invokeBatchViaRabbit(actions);
        try {
            Thread.sleep(sleep * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new RestTemplate().getForObject("http://localhost:8080/test/counter", Integer.class));
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
