package com.safeway.tech.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "aws.cognito")
public class CognitoConfig {

    private String userPoolId;
    private String clientId;
    private String jwk;
    private Boolean autoConfirm;
    private String accessKey;
    private String secretKey;
    private String region;

    @Bean
    public CognitoIdentityProviderClient cognitoClient() {
        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String region = System.getenv().getOrDefault("AWS_REGION", "us-east-1");

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        return CognitoIdentityProviderClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.of(region))
                .build();
    }
}