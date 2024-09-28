package de.rjst.rjstbackendservice.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static de.rjst.rjstbackendservice.TestUtil.ANY_STRING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("tests")
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        PlayerEntity player = new PlayerEntity();
        player.setName(ANY_STRING);
        player.setPassword(ANY_STRING);
        playerRepository.save(player);
    }

    @Test
    void getAll() {
        List<PlayerEntity> result = playerRepository.findAll();

        assertThat(result.size()).isEqualTo(1);
    }

}
