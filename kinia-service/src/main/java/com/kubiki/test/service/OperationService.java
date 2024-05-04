package com.kubiki.test.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
@Service
@Log4j2
public class OperationService {

    public int padovanSequence(int number) {
        log.info("Start counting");
        int result = countPadovan(number);
        log.info("Finish counting: result: {}", result);
        return result;
    }

    private int countPadovan(int number) {
        if (number == 1 || number == 2 || number == 3) {
            return 1;
        }
        return countPadovan(number - 1) + countPadovan(number - 2) + countPadovan(number - 3);
    }
}
