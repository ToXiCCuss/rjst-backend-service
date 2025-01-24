package de.rjst.rjstbackendservice.controller;

import de.rjst.rjstbackendservice.TestContainerIT;
import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Base64;

import static de.rjst.rjstbackendservice.TestUtil.ANY_USER_LOGIN;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrivateControllerContainerIT extends TestContainerIT {

    @LocalServerPort
    private Integer port;

    private static final String PLAYER_ENDPOINT = "/private/players";

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    void getPlayer() {
        Player player = new Player();
        player.setId(0L);
        player.setBalance(BigInteger.TEN);
        player.setCreated(LocalDateTime.now());
        player.setUpdated(LocalDateTime.now());

        playerRepository.save(player);
        final Player result = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic " + ANY_USER_LOGIN)
                .when()
                .get(PLAYER_ENDPOINT + "/1")
                .then()
                .statusCode(200)
                .extract().body().as(Player.class);

        assertThat(result).isNotNull();
        assertThat(result.getBalance()).isEqualTo(BigInteger.TEN);
    }

}
