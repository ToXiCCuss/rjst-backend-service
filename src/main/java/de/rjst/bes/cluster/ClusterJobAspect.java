package de.rjst.bes.cluster;


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
public class ClusterJobAspect {

    private final ClusterJobLogicRepository clusterJobLogicRepository;

    @Around("execution(public * * (..)) && @annotation(clusterJob)")
    public Object around(final ProceedingJoinPoint joinPoint, final ClusterJob clusterJob) throws Throwable {
        Object result = null;

        if (clusterJobLogicRepository.tryLock(clusterJob)) {
            result = joinPoint.proceed();
            clusterJobLogicRepository.unlock(clusterJob);
        } else {
            log.debug("Failed to acquire lock for {}", clusterJob.name());
        }
        return result;

    }

}
