package org.depromeet.spot.ncp.config;

import org.depromeet.spot.ncp.property.ObjectStorageProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.RequiredArgsConstructor;

@Configuration
@Profile("!test")
@RequiredArgsConstructor
public class ObjectStorageConfig {

    private final ObjectStorageProperties objectStorageProperties;
    private final Regions clientRegion = Regions.AP_NORTHEAST_2;
    public static final String BUCKET_NAME = "spot-image-bucket-v2";

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
