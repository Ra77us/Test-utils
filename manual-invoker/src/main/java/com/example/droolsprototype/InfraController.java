package com.example.droolsprototype;

import com.kubiki.controller.commons.actions.dtos.infra.ChangePodCPUActionArgs;
import com.kubiki.controller.commons.actions.dtos.infra.CreatePVActionArgs;
import com.kubiki.controller.commons.actions.dtos.infra.CreatePVandPVCActionArgs;
import com.kubiki.controller.commons.actions.dtos.infra.ResizeVolumeActionArgs;
import com.kubiki.controller.commons.definitons.ActionInvoker;
import com.kubiki.controller.sample.actions.ChangeCPUAndPeriodAction;
import com.kubiki.controller.sample.actions.infra.ChangePodCPUAction;
import com.kubiki.controller.sample.actions.infra.CreatePVAction;
import com.kubiki.controller.sample.actions.infra.CreatePVandPVCAction;
import com.kubiki.controller.sample.actions.infra.ResizeVolumeAction;
import com.kubiki.controller.sample.dto.ChangeCPUAndPeriodActionArgs;
import com.kubiki.controller.sample.dto.ChangePeriodActionArgs;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
