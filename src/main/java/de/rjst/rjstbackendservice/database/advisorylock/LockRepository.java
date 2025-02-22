package de.rjst.rjstbackendservice.database.advisorylock;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LockRepository {

    private static final String APP_ID = "appId";
    private static final String LOCK_ID = "lockId";

    private final EntityManager entityManager;

    public final boolean tryAdvisoryLock(final int appId, final int lockId) {
        final Query query = entityManager.createNativeQuery("SELECT pg_try_advisory_xact_lock(:appId, :lockId)");
        query.setParameter(LOCK_ID, lockId);
        query.setParameter(APP_ID, appId);
        return (Boolean) query.getSingleResult();
    }

}
