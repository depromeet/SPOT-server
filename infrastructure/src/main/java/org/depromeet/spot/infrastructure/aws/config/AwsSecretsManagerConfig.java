package org.depromeet.spot.infrastructure.aws.config;

import java.util.Properties;

import javax.sql.DataSource;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Profile("!test")
public class AwsSecretsManagerConfig {

    private static final Logger logger = LoggerFactory.getLogger(AwsSecretsManagerConfig.class);

    private final ConfigurableEnvironment environment;

    @Value("${aws.credentials.access-key}")
    private String accessKey;

    @Value("${aws.credentials.secret-key}")
    private String secretKey;

    @Value("${aws.region.static}")
    private String region;

    @Value("${aws.secretsmanager.name}")
    private String secretsManagerName;

    public AwsSecretsManagerConfig(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Bean
    @Primary
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "!test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @PostConstruct
    public void init() {

        try {
            String secretString = getSecret(secretsManagerName);
            JsonNode secretJson = new ObjectMapper().readTree(secretString);

            Properties properties = new Properties();
            secretJson
                    .fields()
                    .forEachRemaining(
                            entry -> {
                                String key = entry.getKey();
                                String value = entry.getValue().asText();
                                String camelCaseKey = toCamelCase(key);
                                properties.setProperty(camelCaseKey, value);

                                // 콘솔에 키와 값 출력
                                logger.info(
                                        "Loaded secret - Key:{}, Value: {}", camelCaseKey, value);
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

    public String getSecret(String secretName) {
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

    private String toCamelCase(String input) {
        String[] parts = input.split("\\.");
        if (parts.length == 1) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            result.append(parts[i]).append(".");
        }
        String lastPart = parts[parts.length - 1];
        String[] words = lastPart.split("[-_]");
        result.append(words[0].toLowerCase());
        for (int i = 1; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                result.append(Character.toUpperCase(words[i].charAt(0)))
                        .append(words[i].substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
}
