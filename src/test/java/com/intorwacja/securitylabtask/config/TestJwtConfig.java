package com.intorwacja.securitylabtask.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.crypto.spec.SecretKeySpec;

@TestConfiguration
public class TestJwtConfig {

    @Bean
    @Primary
    public String jwtSecret() {
        return "dGVzdC1qd3Qtc2VjcmV0LWZvci10ZXN0aW5nLXB1cnBvc2VzLW9ubHk=";
    }

    @Bean
    @Primary
    public Long jwtExpiration() {
        return 86400000L;
    }

    @Bean
    @Primary
    public SecretKeySpec dataKey() {
        byte[] keyBytes = "test-encryption-key-32-bytes00".getBytes();
        return new SecretKeySpec(keyBytes, "AES");
    }
}
