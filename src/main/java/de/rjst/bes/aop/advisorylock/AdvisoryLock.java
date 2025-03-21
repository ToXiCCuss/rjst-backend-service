package de.rjst.bes.aop.advisorylock;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60)
public @interface AdvisoryLock {

    int appId();
    int lockId() default 0;
}
