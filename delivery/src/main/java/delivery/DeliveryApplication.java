package delivery;

import delivery.common.properties.FileStorageProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@OpenAPIDefinition(servers = {@Server(url = "https://baobab.run", description = "fx server 연습용 도메인")})
@EnableConfigurationProperties({
    FileStorageProperties.class
})
@SpringBootApplication
public class DeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }
}