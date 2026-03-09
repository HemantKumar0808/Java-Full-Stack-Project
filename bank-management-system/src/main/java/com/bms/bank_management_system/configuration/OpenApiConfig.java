package com.bms.bank_management_system.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bank Management System API")
                        .version("v1.0")
                        .description("API documentation for Bank Management System")
                        .contact(new Contact()
                                .name("ADFC Bank")
                                .url("https://adfc-bank.com")
                                .email("")
                        )
                        .license(new License()
                                .name("ADFC Bank License")
                                .url("https://adfc-bank.com/license")
                        )
                );
    }
}
