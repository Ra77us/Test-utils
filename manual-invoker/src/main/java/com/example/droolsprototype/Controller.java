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

import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class Controller {

    private final ActionInvoker actionInvoker;

    @GetMapping("test6")
    public void getMapping() {
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
}
