package com.example.manualinvoker;

import com.kubiki.controller.commons.actions.dtos.infra.*;
import com.kubiki.controller.commons.definitons.ActionInvoker;
import com.kubiki.controller.commons.definitons.ActionScheduleRequest;
import com.kubiki.controller.sample.actions.ChangeCPUAndPeriodAction;
import com.kubiki.controller.sample.actions.ComplexCleanUpAction;
import com.kubiki.controller.sample.actions.FailingAction;
import com.kubiki.controller.sample.actions.infra.ChangePodCPUAction;
import com.kubiki.controller.sample.actions.infra.CreatePVAction;
import com.kubiki.controller.sample.actions.infra.CreatePVandPVCAction;
import com.kubiki.controller.sample.actions.infra.ResizeVolumeAction;
import com.kubiki.controller.sample.dto.ChangeCPUAndPeriodActionArgs;
import com.kubiki.controller.sample.dto.ChangePeriodActionArgs;
import com.kubiki.controller.sample.dto.ComplexCleanUpActionArgs;
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
@RequestMapping("/kinia")
@AllArgsConstructor
public class InfraController {

    private final ActionInvoker actionInvoker;

    @GetMapping("/create-pv-and-pvc")
    public void invokeCreatePvAndPvcAction() {
        actionInvoker.invokeAction(new CreatePVandPVCAction(
                new CreatePVandPVCActionArgs(
                        "kubiki",
                        "pvName",
                        "pvcName",
                        "mystorage",
                        "25Mi",
                        "25Mi",
                        "/mnt/my-pv"

                ), 0, 0), "idempotencyKey", 0, 0);
    }

    @GetMapping("/create-pv")
    public void invokeCreatePv() {
        actionInvoker.invokeAction(new CreatePVAction(
                new CreatePVActionArgs(
                        "v1",
                        "pvName1",
                        "10Mi",
                        "mystorage",
                        "/mnt/path"
                ), 0, 0), "idempotencyKey", 0, 0);
    }

    @GetMapping("/resize-pvc")
    public void invokeResizePVCAction() {
        actionInvoker.invokeAction(new ResizeVolumeAction(
                new ResizeVolumeActionArgs(
                        "pvcName",
                        "10Mi"
                ), 0, 0), "idempotencyKey", 0, 0);
    }

    //todo change args
    @GetMapping("/change-cpu")
    public void invokeChangeCPUAction() {
        actionInvoker.invokeAction(new ChangePodCPUAction(
                new ChangePodCPUActionArgs(
                        "default",
                        "bb-demo-5fdd76997f-77gt7",
                        "2",
                        "1"
                ), 0, 0), "idempotencyKey", 0, 0);
    }

    @GetMapping("/complex")
    public void invokeChangeCPUAndPeriod() {
        actionInvoker.invokeAction((
                new ChangeCPUAndPeriodAction(
                        new ChangeCPUAndPeriodActionArgs(
                                new ChangePodCPUActionArgs(
                                        "kubiki",
                                        "kinia-service",
                                        "3",
                                        "2"
                                ),
                                new ChangePeriodActionArgs(
                                        50,
                                        "complex-service.kubiki:8080")),
                        0, 0)));
    }

    @GetMapping("/clean-up")
    public void invokeCleanUp() {
        actionInvoker.invokeAction(new ComplexCleanUpAction(
                new ComplexCleanUpActionArgs(
                        new CreateStorageClassActionArgs(
                                "test-storage"
                        ),
                        new CreatePVActionArgs(
                                "1",
                                "1",
                                "1",
                                "1",
                                "1"
                        )), 0, 0));
    }

    @GetMapping("test1")
    public void test1() {
        int n = 70;
        Integer check = new RestTemplate().getForObject("http://149.156.182.229:32222/test/counter", Integer.class);
        System.out.println(check);

        List<ActionScheduleRequest> t1 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, 0, 5))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t1", n, t1, 8);

        List<ActionScheduleRequest> t2 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, (19 - i) / 2, 10))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t2", n, t2, 14);

        List<ActionScheduleRequest> t3 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, (19 - i) / 12, 15))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t3", n, t3, 16);

        List<ActionScheduleRequest> t4 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, (19 - i) / 7, 3))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t4", n, t4, 7);

        List<ActionScheduleRequest> t5 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, (19 - i) / 10, 7))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t5", n, t5, 17);

        List<ActionScheduleRequest> t6 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, 19 - i, 19))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t6", n, t6, 24);
        List<ActionScheduleRequest> t7 = IntStream.range(0, 20).mapToObj(i -> getActions(1, i, (19 - i) / 5, 7))
                .flatMap(Function.identity()).collect(toCollection(ArrayList::new));
        performTests("t7", n, t7, 9);
    }

    @GetMapping("test6")
    public void test6() {
        Integer check = new RestTemplate().getForObject("http://149.156.182.229:32222/test/success-counter", Integer.class);
        System.out.println(check);
        for (int retry = 0; retry < 6; retry++) {
            int finalRetry = retry;
            List.of(5, 15, 30, 50, 60, 80).forEach(fail -> {
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

    private Stream<ActionScheduleRequest> getActions(int count, int priority, int delay, long window) {
        return NumberAndDelayActionFactory.getActions(count, priority, delay, window);
    }

    private void performTests(String name, int n, List<ActionScheduleRequest> actions, int sleep) {
        System.out.println(name);
        for (int i = 0; i < n; i++) {
            performTest(actions, sleep);
        }
    }

    private void performTest(List<ActionScheduleRequest> actions, int sleep) {
        Collections.shuffle(actions);
        actionInvoker.invokeBatchViaRabbit(actions);
        try {
            Thread.sleep(sleep * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new RestTemplate().getForObject("http://149.156.182.229:32222/test/counter", Integer.class));
    }
}
