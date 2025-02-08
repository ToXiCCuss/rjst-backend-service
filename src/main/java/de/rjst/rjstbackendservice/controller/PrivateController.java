package de.rjst.rjstbackendservice.controller;

import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.logic.PlayerService;
import de.rjst.rjstbackendservice.logic.TestDataGeneratorFunction;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("private")
public class PrivateController {

    private final PlayerService playerService;
    private final TestDataGeneratorFunction testDataGeneratorFunction;


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("players")
    public ResponseEntity<List<Player>> getPlayers(HttpServletRequest request) {
        request.getHeaderNames().asIterator().forEachRemaining(System.out::println);
        return new ResponseEntity<>(playerService.getPlayers(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable final Long id) {
        return new ResponseEntity<>(playerService.getPlayerById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("players")
    public ResponseEntity<Player> postPlayer(@RequestBody final Player player) {
        return new ResponseEntity<>(playerService.postPlayer(player), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("players")
    public ResponseEntity<Player> putPlayer(@RequestBody final Player player) {
        return new ResponseEntity<>(playerService.updatePlayer(player), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("players/{id}")
    public ResponseEntity<Boolean> deletePlayer(@PathVariable final Long id) {
        return ResponseEntity.ok(playerService.deletePlayer(id));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("players/testdata")
    public ResponseEntity<List<Player>> generateTestData(@RequestParam final Long amount) {
        return ResponseEntity.ok(testDataGeneratorFunction.apply(amount));
    }
}
