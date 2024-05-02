package com.example.droolsprototype;

import com.kubiki.controller.commons.actions.dtos.MockRetryActionArgs;
import com.kubiki.controller.commons.definitons.ActionInvoker;
import com.kubiki.controller.sample.actions.MockRetryAction;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class Controller {

    private final ActionInvoker actionInvoker;

    @GetMapping()
    public void getMapping() {
        actionInvoker.invokeViaRabbit(new MockRetryAction(new MockRetryActionArgs("fdsfsd"), 1), "", 0, 30 );
    }
}
