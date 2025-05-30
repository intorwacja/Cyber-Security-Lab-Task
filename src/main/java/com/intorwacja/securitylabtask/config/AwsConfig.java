package com.intorwacja.securitylabtask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;

import java.net.URI;

@Configuration
public class AwsConfig {

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
}