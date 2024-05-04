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

import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class Controller {

    private final ActionInvoker actionInvoker;

    @GetMapping()
    public void getMapping() {
        actionInvoker.invokeAction(new ChangeApplicationThreadsNumberAction(new ChangeApplicationThreadsNumberArgs(1, "http://" + "149.156.182.229:30111" + "/limited-threads/set-threads"
               )));
    }

    @GetMapping("test6")
    public void test6() {
        ActionScheduleRequest request = new ActionScheduleRequest(new FailingAction(99, 0));
        actionInvoker.invokeBatchViaRabbit(IntStream.range(0, 1000).mapToObj(i -> request).toList());
    }
}
