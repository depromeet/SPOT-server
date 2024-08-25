package org.depromeet.spot.usecase.service.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.function.Executable;

public class ConcurrencyTest {

    private static final int NUMBER_OF_THREAD = 100;
    private static final int NUMBER_OF_THREAD_POOL = 5;

    private static final ExecutorService executorService =
            Executors.newFixedThreadPool(NUMBER_OF_THREAD_POOL);
    private static final CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREAD);

    public AtomicLong execute(Executable executable, AtomicLong exceptionCount)
            throws InterruptedException {
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            executorService.execute(
                    () -> {
                        try {
                            executable.execute();
                            System.out.println(
                                    "Thread " + Thread.currentThread().getId() + " - 성공");
                        } catch (Throwable e) {
                            exceptionCount.getAndIncrement();
                            System.out.println(
                                    "Thread " + Thread.currentThread().getId() + " - 실패");
                        } finally {
                            latch.countDown();
                        }
                    });
        }
        latch.await();
        return exceptionCount;
    }
}
