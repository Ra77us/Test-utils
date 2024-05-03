package com.kubiki.controller.sample.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeApplicationThreadsNumberArgs {
    private int threads;
    private String url;
}