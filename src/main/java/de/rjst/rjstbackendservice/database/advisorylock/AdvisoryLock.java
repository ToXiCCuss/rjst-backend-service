package de.rjst.rjstbackendservice.database.advisorylock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AdvisoryLock {

    int appId();
    int lockId() default 0;
}
