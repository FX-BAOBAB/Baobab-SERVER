package warehouse;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import warehouse.domain.image.properties.FileStorageProperties;

@OpenAPIDefinition(servers = {@Server(url = "https://baobab.run", description = "fx server 연습용 도메인")})
@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class WarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }
}