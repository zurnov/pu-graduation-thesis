package com.zurnov.restaurant;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Restaurant API",
        version = "1.0.0"),
        servers = @Server(url = "http://localhost:8080/"),
        tags = {
                @Tag(name = "Order", description = "Order Controller"),
                @Tag(name = "Order Status", description = "Order Status Controller"),
                @Tag(name = "Product Category", description = "Product Category Controller"),
                @Tag(name = "Product", description = "Product Controller"),
                @Tag(name = "Restaurant Table", description = "Restaurant Table Controller"),
                @Tag(name = "Role", description = "Role Controller"),
                @Tag(name = "User", description = "User Controller")
        }
)
public class RestaurantApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }

}
