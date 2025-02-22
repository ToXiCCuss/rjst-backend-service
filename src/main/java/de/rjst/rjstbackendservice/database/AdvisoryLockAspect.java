package de.rjst.rjstbackendservice.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class AdvisoryLockAspect {

    private final LockRepository lockRepository;

    @Around("@annotation(advisoryLock)")
    public Object around(final ProceedingJoinPoint joinPoint, final AdvisoryLock advisoryLock) throws Throwable {
        Object result = null;
        final long lockKey = advisoryLock.key();

        final var lockAcquired = lockRepository.tryAdvisoryLock(lockKey);
        if (lockAcquired) {
            result = joinPoint.proceed();
        } else {
            log.warn("Failed to acquire lock with key {}", lockKey);
        }
        return result;
    }
}
