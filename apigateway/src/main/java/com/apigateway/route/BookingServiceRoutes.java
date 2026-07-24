package com.apigateway.route;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class BookingServiceRoutes {
        @Bean
        public RouterFunction<ServerResponse> bookingRoutes() {
                return route("booking-service")
                                .POST("/api/v1/booking",
                                                http())
                                .before(uri("http://localhost:8081/api/v1/booking"))
                                /* .filter(circuitBreaker(config -> config
                                                .setId("bookingServiceCircuitBreaker")
                                                .setFallbackUri("forward:/fallbackRoute"))) */
                                .build();
        }

}

// .forward("http://localhost:8081/api/v1/booking")
// BeforeFilterFunctions.uri("http://localhost:8081/api/v1/booking")