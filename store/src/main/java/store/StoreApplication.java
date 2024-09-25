package store;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import store.domain.image.properties.FileStorageProperties;

//@OpenAPIDefinition(servers = {@Server(url = "https://baobab.run", description = "fx server 연습용 도메인")})
@EnableConfigurationProperties({
    FileStorageProperties.class
})
@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }
}
