package de.rjst.rjstbackendservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaReader {

    @KafkaListener(topics = "baeldung")
    public void listen(String message) {
        log.info("Received Messasge: " + message);
    }

}
