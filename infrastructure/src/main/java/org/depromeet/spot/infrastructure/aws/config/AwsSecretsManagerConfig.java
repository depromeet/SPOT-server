package org.depromeet.spot.infrastructure.aws.config;

import java.util.Properties;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Order(Integer.MIN_VALUE)
public class AwsSecretsManagerConfig {

    private static final Logger logger = LoggerFactory.getLogger(AwsSecretsManagerConfig.class);

    private final ConfigurableEnvironment environment;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    public AwsSecretsManagerConfig(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void init() {
        String activeProfile = environment.getActiveProfiles()[0];
        //        String secretName = "spot-secrets-" + activeProfile;
        String secretName = "/secret/spot";

        try {
            String secretString = getSecret(secretName);
            JsonNode secretJson = new ObjectMapper().readTree(secretString);

            Properties properties = new Properties();
            secretJson
                    .fields()
                    .forEachRemaining(
                            entry -> {
                                String key = entry.getKey();
                                String value = entry.getValue().asText();
                                properties.setProperty(key, value);

                                // 콘솔에 키와 값 출력
                                //                                logger.info("Loaded secret - Key:
                                // {}, Value: {}", key, value);
                            });

            MutablePropertySources propertySources = environment.getPropertySources();

            // 가장 큰 우선순위를 가지게 함.
            propertySources.addFirst(new PropertiesPropertySource("aws-secrets", properties));

            logger.info(
                    "Successfully loaded and applied {} secrets from AWS Secrets Manager",
                    properties.size());

        } catch (Exception e) {
            throw new RuntimeException("Could not load secrets from AWS Secrets Manager", e);
        }
    }

    private String getSecret(String secretName) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        AWSSecretsManager client =
                AWSSecretsManagerClientBuilder.standard()
                        .withRegion(region)
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                        .build();

        GetSecretValueRequest getSecretValueRequest =
                new GetSecretValueRequest().withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);

        return getSecretValueResult.getSecretString();
    }
}
