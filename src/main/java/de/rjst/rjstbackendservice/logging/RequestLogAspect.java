package de.rjst.rjstbackendservice.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestLogAspect {

    @Around("@annotation(requestLog)")
    public Object around(final ProceedingJoinPoint joinPoint, final RequestLog requestLog) throws Throwable {
        final String key = requestLog.key();
        MDC.put(key, requestLog.value());
        try {
            return joinPoint.proceed();
        } finally {
            MDC.remove(key);
        }
    }

}
