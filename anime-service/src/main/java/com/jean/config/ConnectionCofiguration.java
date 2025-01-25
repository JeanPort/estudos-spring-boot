package com.jean.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ConnectionCofiguration {

    @Value("${database.url}")
    private String url;
    @Value("${database.username}")
    private String usename;
    @Value("${database.password}")
    private String password;

    @Bean
    @Primary
    public Connection connectionMySQL() {
        return new Connection(url,usename,password);
    }

    @Bean
    public Connection connectionMongo() {
        return new Connection(url,usename,password);
    }
}
