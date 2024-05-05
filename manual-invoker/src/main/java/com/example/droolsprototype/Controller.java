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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("test1")
    public void test1() {
        int n = 10;
        Integer check = new RestTemplate().getForObject("http://149.156.182.229:32222/test/counter", Integer.class);
        System.out.println(check);
        List<ActionScheduleRequest> t1 =  getActions(20, 0, 0, 7).collect(toCollection(ArrayList::new));
        performTests("t1", n, t1, 10);
//
//        List<ActionScheduleRequest> t2 = IntStream.range(0, 100).mapToObj(i -> getActions(1, i, i / 10, 25))
//                .flatMap(Function.identity()).toList();
//        performTests("t2", n, t2, 25);
//
//        List<ActionScheduleRequest> t3 = IntStream.range(0, 100).mapToObj(i -> getActions(1, i, (i / 10) * 2, 30))
//                .flatMap(Function.identity()).toList();
//        performTests("t3", n, t3, 30);
//
//        List<ActionScheduleRequest> t4 = IntStream.range(0, 100).mapToObj(i -> getActions(1, i, i / 4, 35))
//                .flatMap(Function.identity()).toList();
//        performTests("t4", n, t4, 35);
//
//        List<ActionScheduleRequest> t5 = IntStream.range(0, 100).mapToObj(i -> getActions(1, i, i, 105))
//                .flatMap(Function.identity()).toList();
//        performTests("t5", n, t5, 105);
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
        System.out.println(new RestTemplate().getForObject("http://149.156.182.229:32222/test/counter", Integer.class));
    }
}
