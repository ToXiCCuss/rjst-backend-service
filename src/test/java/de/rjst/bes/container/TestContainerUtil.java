package de.rjst.bes.container;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;

import java.nio.charset.StandardCharsets;

@UtilityClass
public class TestContainerUtil {

    private final String PATH = "/response/%s";

    @SneakyThrows
    public String getResponse() {
        final InputStreamSource source = new ClassPathResource(PATH.formatted("ip.json"));
        final var inputStream = source.getInputStream();
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

}
