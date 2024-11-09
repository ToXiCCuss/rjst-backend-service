package de.rjst.rjstbackendservice.database;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    String SKIP_LOCKED = "-2";

//    @QueryHints(@QueryHint(name = AvailableSettings.JAKARTA_LOCK_TIMEOUT, value = SKIP_LOCKED))
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<PlayerEntity> findTop50ByProcessStateOrderByIdAsc(ProcessState processState);
}
