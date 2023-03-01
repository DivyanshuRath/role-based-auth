package com.deloitte.myproject.config;

// Importing required classes
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    

}

