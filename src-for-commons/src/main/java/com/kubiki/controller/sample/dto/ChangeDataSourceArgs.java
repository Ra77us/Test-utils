package com.kubiki.controller.sample.dto;

import lombok.Data;

@Data
public class ChangeDataSourceArgs {
    private DataSource dataSource;
    private String serviceUrl;
}
