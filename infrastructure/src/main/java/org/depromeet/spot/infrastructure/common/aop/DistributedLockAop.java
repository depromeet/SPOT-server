package org.depromeet.spot.infrastructure.common.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.depromeet.spot.common.annotation.DistributedLock;
import org.depromeet.spot.infrastructure.common.util.SpringELParser;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

    private final RedissonClient redissonClient;
    private final TransactionAop aopForTransaction;

    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    @Around("@annotation(org.depromeet.spot.common.annotation.DistributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String lockKey = generateLockKey(signature, joinPoint.getArgs(), distributedLock);
        RLock rLock = redissonClient.getLock(lockKey);

        try {
            if (!acquireLock(rLock, distributedLock)) {
                return false;
            }
            return aopForTransaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw e;
        } finally {
            releaseLock(rLock);
        }
    }

    private String generateLockKey(
            MethodSignature signature, Object[] args, DistributedLock distributeLock) {
        return REDISSON_LOCK_PREFIX
                + SpringELParser.getDynamicValue(
                        signature.getParameterNames(), args, distributeLock.key());
    }

    private boolean acquireLock(RLock rLock, DistributedLock distributeLock)
            throws InterruptedException {
        return rLock.tryLock(
                distributeLock.waitTime(), distributeLock.leaseTime(), distributeLock.timeUnit());
    }

    private void releaseLock(RLock rLock) {
        if (rLock != null && rLock.isHeldByCurrentThread()) {
            rLock.unlock();
        }
    }
}
