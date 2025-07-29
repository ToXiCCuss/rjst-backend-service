package de.rjst.bes.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.redis.testcontainers.RedisContainer;
import de.rjst.bes.adapter.IpQueryResponse;
import de.rjst.bes.cache.CacheNames;
import de.rjst.bes.container.ContainerTest;
import de.rjst.bes.container.IpServiceMock;
import de.rjst.bes.database.Player;
import de.rjst.bes.database.PlayerRepository;
import de.rjst.bes.datasource.CacheCleaner;
import de.rjst.bes.datasource.ContainerPauser;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.math.BigInteger;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;

@ContainerTest
class PrivateControllerContainerIT {

    @LocalServerPort
    private Integer port;

    private static final String PLAYERS = "/private/players";
    private static final String IP_SEARCH = "/private/ipsearch";

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CacheCleaner cacheCleaner;

    @Autowired
    private IpServiceMock ipServiceMock;

    @Autowired
    private RedisContainer redisContainer;

    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    @BeforeEach
    void setUp() {
        final var requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setPort(port);
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        final var responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();

        cacheCleaner.run();
        playerRepository.deleteAll();
    }

    @Test
    void getPlayer() {
        var player = new Player();
        player.setBalance(BigInteger.TEN);
        player.setCreated(LocalDateTime.now());
        player.setUpdated(LocalDateTime.now());
        player = playerRepository.saveAndFlush(player);

        final Player result = given()
            .spec(requestSpecification)
            .get(PLAYERS + "/" + player.getId())
            .then()
            .spec(responseSpecification)
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .as(Player.class);

        assertThat(result.getBalance()).isEqualTo(BigInteger.TEN);
        assertThat(result.getId()).isEqualTo(player.getId());

        final var cache = cacheManager.getCache(CacheNames.PLAYER);
        final var cachedPlayer = cache.get(result.getId(), Player.class);

        assertThat(cachedPlayer.getId()).isEqualTo(result.getId());
    }


    @Test
    void getPlayer2() {
        var player = new Player();
        player.setBalance(BigInteger.TEN);
        player.setCreated(LocalDateTime.now());
        player.setUpdated(LocalDateTime.now());
        player = playerRepository.saveAndFlush(player);

        given()
            .spec(requestSpecification)
            .get(PLAYERS + "/" + player.getId())
            .then()
            .spec(responseSpecification)
            .statusCode(HttpStatus.OK.value());

        ContainerPauser.pause(redisContainer);

        given()
            .spec(requestSpecification)
            .get(PLAYERS + "/" + player.getId())
            .then()
            .spec(responseSpecification)
            .statusCode(HttpStatus.OK.value());

        ContainerPauser.unpause(redisContainer);
    }



    @Test
    void getIP() {
        final var ip = "1.1.1.1";
        ipServiceMock.getIpQueryResponse(ip);

        final IpQueryResponse result = given()
            .spec(requestSpecification)
            .when()
            .param("ip", ip)
            .get(IP_SEARCH)
            .then()
            .spec(responseSpecification)
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .as(IpQueryResponse.class);

        final var isp = result.getIsp();
        assertThat(result.getIp()).isEqualTo(ip);
        assertThat(isp.getOrg()).isEqualTo("VSE NET GmbH");
    }

}
