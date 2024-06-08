package de.rjst.rjstbackendservice.controller.rest;

import de.rjst.rjstbackendservice.database.PlayerEntity;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.logic.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@RestController
@RequestMapping("private")
public class PrivateController {

    private final PlayerService playerService;


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("players")
    public ResponseEntity<List<PlayerEntity>> getPlayers() {
        return new ResponseEntity<>(playerService.getPlayers(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("players/{id}")
    public ResponseEntity<PlayerEntity> getPlayer(@PathVariable final Long id) {
        return new ResponseEntity<>(playerService.getPlayerById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("players")
    public ResponseEntity<PlayerEntity> postPlayer(@RequestBody final PlayerEntity player) {
        return new ResponseEntity<>(playerService.postPlayer(player), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("players")
    public ResponseEntity<PlayerEntity> putPlayer(@RequestBody final PlayerEntity player) {
        return new ResponseEntity<>(playerService.updatePlayer(player), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("players/{id}")
    public ResponseEntity<Boolean> deletePlayer(@PathVariable final Long id) {
        return ResponseEntity.ok(playerService.deletePlayer(id));
    }
}
