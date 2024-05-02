package com.kubiki.controller.sample.dto;

import lombok.Data;

@Data
public class ScaleHeavyFlowActionArgs {
    int upstreamThreads;
    String upstreamService;
    int downstreamCpu;
    String downstreamPod;
    String downstreamNamespace;
}
