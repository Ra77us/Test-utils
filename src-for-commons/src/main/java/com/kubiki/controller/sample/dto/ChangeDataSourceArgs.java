package com.kubiki.controller.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChangeDataSourceArgs {
    private DataSource dataSource;
    private String serviceUrl;
}
