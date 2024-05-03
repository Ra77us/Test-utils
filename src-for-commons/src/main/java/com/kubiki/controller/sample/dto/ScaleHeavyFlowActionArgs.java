package com.kubiki.controller.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ScaleHeavyFlowActionArgs {
    int upstreamThreads;
    String upstreamService;
    double downstreamCpu;
    String downstreamPod;
    String downstreamNamespace;
}
