package org.depromeet.spot.infrastructure.aws.config;

import org.depromeet.spot.infrastructure.aws.property.ObjectStorageProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.RequiredArgsConstructor;

@Configuration
// @Profile("!test")
@RequiredArgsConstructor
public class ObjectStorageConfig {

    private final ObjectStorageProperties objectStorageProperties;
    private final Regions clientRegion = Regions.AP_NORTHEAST_2;
    private static final String REGION = "kr-standard";

    @Bean
    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        objectStorageProperties.accessKey(),
                                        objectStorageProperties.secretKey())))
                .build();
    }
}
