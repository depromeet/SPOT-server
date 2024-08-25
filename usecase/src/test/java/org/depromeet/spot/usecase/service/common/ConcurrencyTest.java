package org.depromeet.spot.usecase.service.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.function.Executable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcurrencyTest {

    private static final int NUMBER_OF_THREAD = 100;
    private static final int NUMBER_OF_THREAD_POOL = 5;

    private ExecutorService executorService;
    private CountDownLatch latch;

    public AtomicLong execute(Executable executable, AtomicLong exceptionCount)
            throws InterruptedException {
        this.executorService = Executors.newFixedThreadPool(NUMBER_OF_THREAD_POOL);
        this.latch = new CountDownLatch(NUMBER_OF_THREAD);

        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
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
