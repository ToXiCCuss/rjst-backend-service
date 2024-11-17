package de.rjst.rjstbackendservice.database;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {


    List<Player> findDistinctByUpdatedBefore(LocalDateTime updated, Pageable pageable);
}
