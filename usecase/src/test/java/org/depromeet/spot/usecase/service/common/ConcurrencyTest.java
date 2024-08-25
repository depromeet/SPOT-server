package org.depromeet.spot.usecase.service.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.function.Executable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcurrencyTest {

    private final int numberOfThread;

    private ExecutorService executorService;
    private CountDownLatch latch;

    public ConcurrencyTest(final int numberOfThread) {
        this.numberOfThread = numberOfThread;
    }

    public AtomicLong execute(Executable executable) throws InterruptedException {
        this.executorService = Executors.newFixedThreadPool(numberOfThread);
        this.latch = new CountDownLatch(numberOfThread);

        AtomicLong exceptionCount = new AtomicLong();
        for (int i = 0; i < numberOfThread; i++) {
            executorService.execute(
                    () -> {
                        try {
                            executable.execute();
                            log.info("Thread " + Thread.currentThread().getId() + " - 성공");
                        } catch (Throwable e) {
                            exceptionCount.getAndIncrement();
                            log.info("Thread " + Thread.currentThread().getId() + " - 실패");
                        } finally {
                            latch.countDown();
                        }
                    });
        }
        latch.await();
        executorService.shutdown();
        return exceptionCount;
    }
}
