package com.kubiki.test.service;

import org.springframework.stereotype.Service;

@Service
public class CpuHeavyService {

    public int compute() {
        return compute(45);
    }

    private int compute(int iter) {
        if (iter == 1 || iter == 2) {
            return 1;
        }
        return compute(iter - 1) + compute(iter - 2);
    }
}
