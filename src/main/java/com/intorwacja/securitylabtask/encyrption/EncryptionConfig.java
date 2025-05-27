package com.intorwacja.securitylabtask.encyrption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.DataKeySpec;
import software.amazon.awssdk.services.kms.model.GenerateDataKeyResponse;

import javax.crypto.spec.SecretKeySpec;
import java.net.URI;

@Configuration
class EncryptionConfig {

    @Value("${app.crypto.key-id}")
    private String keyId;

    @Value("${aws.key}")
    private String awsKey;

    @Value("${aws.secret}")
    private String awsSecret;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.endpoint}")
    private String awsEndpoint;

    @Bean
    public KmsClient kmsClient() {
        return KmsClient.builder()
                .endpointOverride(URI.create(awsEndpoint))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(awsKey, awsSecret)))
                .region(Region.of(awsRegion))
                .build();
    }

    @Bean
    public SecretKeySpec dataKey(KmsClient kmsClient) {
        GenerateDataKeyResponse resp = kmsClient.generateDataKey(r -> r
                .keyId(keyId)
                .keySpec(DataKeySpec.AES_256));
        return new SecretKeySpec(resp.plaintext().asByteArray(), "AES");
    }
}