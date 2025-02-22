package de.rjst.rjstbackendservice.database.advisorylock;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LockRepository {

    private static final String LOCK_QUERY = "SELECT pg_try_advisory_xact_lock(:lockId)";
    private static final String APP_ID = "appId";
    private static final String LOCK_ID = "lockId";

    private final EntityManager entityManager;


    public boolean tryAdvisoryLock(final long lockId) {
        final Query query = entityManager.createNativeQuery(LOCK_QUERY);
        query.setParameter(LOCK_ID, lockId);
        return (Boolean) query.getSingleResult();
    }

}
