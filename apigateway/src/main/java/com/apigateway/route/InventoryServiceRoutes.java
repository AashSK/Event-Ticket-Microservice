package com.apigateway.route;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class InventoryServiceRoutes {

    @Bean
    public RouterFunction<ServerResponse> inventoryRoute() {
        return route("inventory-service")
                .GET("/api/v1/inventory/**", http())
                .PUT("/api/v1/inventory/**", http())
                .before(uri("http://localhost:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceApiDocs() {
        return route("inventory-service-api-docs")
                .GET("/docs/inventoryservice/v3/api-docs", http())
                .before(uri("http://localhost:8080"))
                .before(setPath("/v3/api-docs"))
                .build();
    }

}
