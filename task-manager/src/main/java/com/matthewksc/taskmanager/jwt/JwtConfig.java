package com.matthewksc.taskmanager.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${application.secret.jwtKey}")
    private String key;

    @Value("${application.secret.jwtHeader}")
    private String header;

    @Value("${application.secret.jwtPrefix}")
    private String prefix;

    public String getKey() {
        return key;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }
}
