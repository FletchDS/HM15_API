package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.LongStream;
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
        long parallelStreamTime = System.currentTimeMillis();
        long sum = LongStream
                .rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
        parallelStreamTime = System.currentTimeMillis() - parallelStreamTime;

        long sequentialStreamTime = System.currentTimeMillis();
        sum = Stream
                .iterate(1L, a -> a +1)
                .limit(1_000_000)
                .reduce(0L, (a, b) -> a + b);
        sequentialStreamTime = System.currentTimeMillis() - sequentialStreamTime;

        if (sequentialStreamTime <= parallelStreamTime){
            logger.debug("Sequential stream completed faster than parallel. " +
                    "It took {} ms for parallel stream and {} ms for sequential", parallelStreamTime, sequentialStreamTime);
        } else {
            logger.debug("Parallel stream completed faster than sequential. " +
                    "It took {} ms for parallel stream and {} ms for sequential", parallelStreamTime, sequentialStreamTime);
        }
        return sum;
    }
}
