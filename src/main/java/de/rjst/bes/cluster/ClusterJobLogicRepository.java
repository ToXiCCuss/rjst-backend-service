package de.rjst.bes.cluster;

import de.rjst.bes.aop.advisorylock.AdvisoryLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ClusterJobLogicRepository {

    private final AdvisoryLockRepository advisoryLockRepository;
    private final ClusterJobRepository clusterJobRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean tryLock(ClusterJob clusterJob) {
        boolean result = false;

        var name = clusterJob.name();
        var appId = clusterJob.appId();
        var lockId = clusterJob.lockId();

        if (advisoryLockRepository.tryLock(appId, lockId)) {
            var optionalClusterJob = clusterJobRepository.findById(name);
            if (optionalClusterJob.isEmpty()) {
                var clusterJobLock = getClusterJobLock(name);
                clusterJobRepository.saveAndFlush(clusterJobLock);
                result = true;
            }
        }

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void unlock(ClusterJob clusterJob) {
        var name = clusterJob.name();

        var optionalClusterJob = clusterJobRepository.findById(name);
        optionalClusterJob.ifPresent(clusterJobRepository::delete);
    }

    private static ClusterJobEntity getClusterJobLock(String name) {
        var result = new ClusterJobEntity();
        result.setId(name);
        result.setHostname(HostnameSupplier.getHostname());
        result.setCreated(LocalDateTime.now());
        return result;
    }
}
