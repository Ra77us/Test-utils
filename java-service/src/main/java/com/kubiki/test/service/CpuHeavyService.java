package com.kubiki.test.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class CpuHeavyService {

    @Value("${test3.num}")
    private Integer num;

    public int compute() {
        return compute(num);
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
