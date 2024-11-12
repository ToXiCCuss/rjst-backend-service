package de.rjst.rjstbackendservice.database;

public interface LockRepository {

    boolean tryAdvisoryLock();
    void releaseAdvisoryLock();

}
