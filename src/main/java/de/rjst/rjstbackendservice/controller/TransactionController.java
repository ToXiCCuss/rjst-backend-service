package de.rjst.rjstbackendservice.controller;


import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final PlayerRepository playerRepository;

    @PreAuthorize("hasAuthority('USER')")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PostMapping("/{playerId}")
    public ResponseEntity<Player> createTransaction(@PathVariable Long playerId, @RequestParam BigInteger amount) {

        Optional<Player> byId = playerRepository.findById(playerId);
        if (byId.isPresent()) {
            Player player = byId.get();
            player.setBalance(player.getBalance().add(amount));
            try {
                Thread.sleep(1000 * 30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok(playerRepository.save(player));
        }
        return ResponseEntity.ok().build();
    }

}
