package com.diligent.expense_tracker.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI expenseTrackerOpenAPI() {

        return new OpenAPI().info(new Info().title("Smart Expense Tracker API").description("REST API for managing personal expenses.")
                .version("1.0.0").contact(new Contact().name("Rakshit Saxena").url("https://github.com/rakshitsaxena07")));
    }
}