package warehouse;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import warehouse.domain.image.properties.FileStorageProperties;

@OpenAPIDefinition(servers = {@Server(url = "https://baobab.run/warehouse/", description = "Baobab Dev Server API")})
@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
@EnableScheduling
public class WarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }
}