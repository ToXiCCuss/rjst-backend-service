package de.rjst.rjstbackendservice.controller;

import de.rjst.rjstbackendservice.TestContainerConfig;
import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static de.rjst.rjstbackendservice.TestUtil.ANY_USER_LOGIN;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ImportTestcontainers(TestContainerConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrivateControllerContainerIT {

    @LocalServerPort
    private Integer port;

    private static final String PLAYER_ENDPOINT = "/private/players";

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void getPlayer() {
        var player = new Player();
        player.setBalance(BigInteger.TEN);
        player.setCreated(LocalDateTime.now());
        player.setUpdated(LocalDateTime.now());
        player = playerRepository.saveAndFlush(player);

        final Player result = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic " + ANY_USER_LOGIN)
                .log().all()
                .when()
                .get(PLAYER_ENDPOINT + "/" + player.getId())
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(Player.class);

        assertThat(result.getBalance()).isEqualTo(BigInteger.TEN);
        assertThat(result.getId()).isEqualTo(player.getId());
    }

}
