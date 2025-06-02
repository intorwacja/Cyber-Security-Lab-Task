
package com.intorwacja.securitylabtask.encyrption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.DataKeySpec;
import software.amazon.awssdk.services.kms.model.GenerateDataKeyResponse;

import javax.crypto.spec.SecretKeySpec;

@Configuration
class EncryptionConfig {

    @Value("${app.crypto.key-id}")
    private String keyId;

    @Bean
    public SecretKeySpec dataKey(KmsClient kmsClient) {
        GenerateDataKeyResponse resp = kmsClient.generateDataKey(r -> r
                .keyId(keyId)
                .keySpec(DataKeySpec.AES_256));
        return new SecretKeySpec(resp.plaintext().asByteArray(), "AES");
    }
}