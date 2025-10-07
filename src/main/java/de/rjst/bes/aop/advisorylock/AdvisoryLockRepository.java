package de.rjst.bes.aop.advisorylock;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AdvisoryLockRepository {

    private static final String LOCK_QUERY = "select pg_try_advisory_xact_lock(:appId, :lockId)";
    private static final String APP_ID = "appId";
    private static final String LOCK_ID = "lockId";

    private final EntityManager entityManager;

    public boolean tryLock(final int appId, final int lockId) {
        final Query query = entityManager.createNativeQuery(LOCK_QUERY);
        query.setParameter(APP_ID, appId);
        query.setParameter(LOCK_ID, lockId);
        return (Boolean) query.getSingleResult();
    }

}
