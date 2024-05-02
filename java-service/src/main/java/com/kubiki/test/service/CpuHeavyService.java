package com.kubiki.test.service;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Log4j2
public class CpuHeavyService {

    @Value("${test3.num}")
    private Integer num;

    public int compute() {
        log.info("Compute cpu heavy service");
        int res = compute(num);
        log.info("Compute cpu heavy service finished");
        return res;
    }

    private int compute(int iter) {
        if (iter == 1 || iter == 2) {
            return 1;
        }
        return compute(iter - 1) + compute(iter - 2);
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
