package fault;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {@Server(url = "https://baobab.run/fault/", description = "Baobab Dev Server API")})
@SpringBootApplication
public class FaultApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaultApplication.class, args);
    }

}
