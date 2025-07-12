package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;


@Service
public class InfoService {

    @Value("${server.port:-1}")
    int port;

    private Logger logger = LoggerFactory.getLogger(InfoService.class);

    public InfoService() {

    }

    public int getPort() {
        logger.debug("Method getPort was called");
        return port;
    }

    public Long getResultOfFormula() {
        logger.debug("Method getResultOfFormula was called");
        long sum = 0;

        long loopTime = System.currentTimeMillis();
        for (int i = 1; i <= 1_000_000; i++) {
            sum += i;
        }
        loopTime = System.currentTimeMillis() - loopTime;

        long sequentialStreamTime = System.currentTimeMillis();
        sum = Stream
                .iterate(1L, a -> a + 1)
                .limit(1_000_000)
                .reduce(0L, (a, b) -> a + b);
        sequentialStreamTime = System.currentTimeMillis() - sequentialStreamTime;

        if (sequentialStreamTime <= loopTime) {
            logger.debug("Sequential stream completed faster than loop. " +
                    "It took {} ms for loop and {} ms for sequential stream", loopTime, sequentialStreamTime);
        } else {
            logger.debug("loop completed faster than sequential stream. " +
                    "It took {} ms for loop and {} ms for sequential stream", loopTime, sequentialStreamTime);
        }
        return sum;
    }
}
