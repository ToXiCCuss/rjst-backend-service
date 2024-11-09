package de.rjst.rjstbackendservice.processing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfig {

    private static final int THREADS = 50;

    @Bean(name = "jobTaskExecutor")
    public Executor taskExecutor() {
        return Executors.newFixedThreadPool(THREADS);
    }

}
