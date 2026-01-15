package com.sparta.spartaapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public OpenAPI spartaAcademyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sparta Academy API")
                        .description("REST API for managing trainers, trainees, and courses")
                        .version("1.0"));
    }
}
