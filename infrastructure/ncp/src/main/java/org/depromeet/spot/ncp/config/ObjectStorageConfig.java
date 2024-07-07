package org.depromeet.spot.ncp.config;

import org.depromeet.spot.ncp.property.ObjectStorageProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ObjectStorageConfig {

    private final ObjectStorageProperties objectStorageProperties;

    @Bean
    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new EndpointConfiguration(
                                objectStorageProperties.endPoint(),
                                objectStorageProperties.region()))
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        objectStorageProperties.accessKey(),
                                        objectStorageProperties.secretKey())))
                .build();
    }
}
