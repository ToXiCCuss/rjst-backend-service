package de.rjst.bes.controller;

import de.rjst.bes.adapter.IpQueryResponse;
import de.rjst.bes.container.IpServiceMock;
import de.rjst.bes.container.JUnitContainersConfiguration;
import de.rjst.bes.database.Player;
import de.rjst.bes.database.PlayerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static de.rjst.bes.TestUtil.ANY_USER_LOGIN;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(JUnitContainersConfiguration.class)
@ActiveProfiles("container")
class PrivateControllerContainerIT {

    @LocalServerPort
    private Integer port;

    private static final String PLAYERS = "/private/players";
    private static final String IP_SEARCH = "/private/ipsearch";

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
                .header(AUTHORIZATION, ANY_USER_LOGIN)
                .log().all()
                .when()
                .get(PLAYERS + "/" + player.getId())
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(Player.class);

        assertThat(result.getBalance()).isEqualTo(BigInteger.TEN);
        assertThat(result.getId()).isEqualTo(player.getId());
    }

    @Test
    void getIP() {
        final var ip = "1.1.1.1";
        IpServiceMock.getIpQueryResponse(ip);

        final IpQueryResponse result = given()
                .contentType(ContentType.JSON)
                .header(AUTHORIZATION, ANY_USER_LOGIN)
                .log().all()
                .when()
                .param("ip", ip)
                .get(IP_SEARCH)
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(IpQueryResponse.class);

        var isp = result.getIsp();
        assertThat(result.getIp()).isEqualTo(ip);
        assertThat(isp.getOrg()).isEqualTo("VSE NET GmbH");
    }

}
