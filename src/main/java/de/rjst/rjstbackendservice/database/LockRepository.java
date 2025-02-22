package de.rjst.rjstbackendservice.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LockRepository {

    private final EntityManager entityManager;

    public boolean tryAdvisoryLock(final long lockId) {
        final Query query = entityManager.createNativeQuery("SELECT pg_try_advisory_xact_lock(:lockId)");
        query.setParameter("lockId", lockId);
        return (Boolean) query.getSingleResult();
    }

}
