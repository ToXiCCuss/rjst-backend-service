package de.rjst.rjstbackendservice.aop.advisorylock;

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

    @Around("execution(public * * (..)) && @annotation(advisoryLock)")
    public Object around(final ProceedingJoinPoint joinPoint, final AdvisoryLock advisoryLock) throws Throwable {
        Object result = null;

        final int appId = advisoryLock.appId();
        final int lockId = advisoryLock.lockId();
        if (lockRepository.tryAdvisoryLock(appId, lockId)) {
            result = joinPoint.proceed();
        } else {
            log.debug("Failed to acquire lock with appId {}, lockId {}", appId, lockId);
        }
        return result;
    }
}
