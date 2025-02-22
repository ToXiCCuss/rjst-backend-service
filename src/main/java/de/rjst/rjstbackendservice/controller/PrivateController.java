package de.rjst.rjstbackendservice.controller;

import de.rjst.rjstbackendservice.adapter.IpQueryResponse;
import de.rjst.rjstbackendservice.adapter.IpQueryService;
import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.logic.PlayerService;
import de.rjst.rjstbackendservice.logic.TestDataGeneratorConsumer;
import lombok.RequiredArgsConstructor;
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
    private final TestDataGeneratorConsumer testDataGeneratorConsumer;
    private final IpQueryService ipQueryService;


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("players")
    public ResponseEntity<List<Player>> getPlayers() {
        return new ResponseEntity<>(playerService.getPlayers(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("ipsearch")
    public ResponseEntity<IpQueryResponse> getIpInfos(@RequestParam final String ip) {
        return new ResponseEntity<>(ipQueryService.getIpQueryResponse(ip), HttpStatus.OK);
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
    public ResponseEntity<Void> generateTestData(@RequestParam final Long amount) {
        testDataGeneratorConsumer.accept(amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
