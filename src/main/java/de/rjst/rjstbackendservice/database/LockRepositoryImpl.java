package de.rjst.rjstbackendservice.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LockRepositoryImpl implements LockRepository {

    private final EntityManager entityManager;

    private static final long LOCK_ID = 12345L;

    @Override
    public boolean tryAdvisoryLock() {
        Query query = entityManager.createNativeQuery("SELECT pg_try_advisory_lock(:lockId)");
        query.setParameter("lockId", LOCK_ID);
        Boolean result = (Boolean) query.getSingleResult();
        return Boolean.TRUE.equals(result);
    }

    @Override
    public void releaseAdvisoryLock() {
        Query query = entityManager.createNativeQuery("SELECT pg_advisory_unlock(:lockId)");
        query.setParameter("lockId", LOCK_ID);
        query.getSingleResult();
    }
}
