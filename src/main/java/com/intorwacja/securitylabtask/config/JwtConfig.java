package com.intorwacja.securitylabtask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.DataKeySpec;
import software.amazon.awssdk.services.kms.model.GenerateDataKeyResponse;

import java.util.Base64;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret.alias}")
    private String jwtSecretAlias;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Bean
    public String jwtSecret(KmsClient kmsClient) {
        GenerateDataKeyResponse response = kmsClient.generateDataKey(builder -> builder
                .keyId(jwtSecretAlias)
                .keySpec(DataKeySpec.AES_256));

        return Base64.getEncoder().encodeToString(response.plaintext().asByteArray());
    }

    @Bean
    public long jwtExpiration() {
        return jwtExpiration;
    }
}