package com.bookingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookingserviceApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Booking Service API")
                        .description("Booking Service API for Event Ticketing App")
                        .version("v1.0.0"));
    }

}
