package com.kubiki.controller.sample.dto;

import com.kubiki.controller.commons.actions.dtos.infra.CreatePVActionArgs;
import com.kubiki.controller.commons.actions.dtos.infra.CreateStorageClassActionArgs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ChangeDataSourceArgs {
    private DataSource dataSource;
    private String serviceUrl;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ComplexCleanUpActionArgs {
        private CreateStorageClassActionArgs createStorageClassActionArgs;
        private CreatePVActionArgs createPVActionArgs;
    }
}
