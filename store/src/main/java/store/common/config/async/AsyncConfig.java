package store.common.config.async;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Primary
    @Bean(name = "imageUploadExecutor")
    public Executor imageUploadExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(36);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        executor.initialize();

        return executor;
    }
}

