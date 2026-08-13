package com.apigateway.route;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
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
                                .filter(circuitBreaker(config -> config
                                                .setId("bookingServiceCircuitBreaker")
                                                .setFallbackUri("forward:/fallbackRoute")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> fallback() {
                return route("fallbackRoute")
                                .POST("/fallbackRoute",
                                                req -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                                                .body("Booking service is currently down"))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> bookingServiceApiDocs() {
                return route("booking-service-api-docs")
                                .GET("/docs/bookingservice/v3/api-docs", http())
                                .before(uri("http://localhost:8081"))
                                .before(setPath("/v3/api-docs"))
                                .build();
        }

}

// .forward("http://localhost:8081/api/v1/booking")
// BeforeFilterFunctions.uri("http://localhost:8081/api/v1/booking")