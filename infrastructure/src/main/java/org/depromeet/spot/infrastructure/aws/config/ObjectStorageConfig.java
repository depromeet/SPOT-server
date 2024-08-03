package org.depromeet.spot.infrastructure.aws.config;

import org.depromeet.spot.infrastructure.aws.property.ObjectStorageProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.RequiredArgsConstructor;

@Configuration
@Profile("!test")
@RequiredArgsConstructor
public class ObjectStorageConfig {

    private final ObjectStorageProperties objectStorageProperties;
    private static final String ENDPOINT = "https://kr.object.ncloudstorage.com";
    private static final String REGION = "kr-standard";
    public static final String BUCKET_NAME = "spot-image-bucket";

    @Bean
    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new EndpointConfiguration(ENDPOINT, REGION))
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        objectStorageProperties.accessKey(),
                                        objectStorageProperties.secretKey())))
                .build();
    }
}
