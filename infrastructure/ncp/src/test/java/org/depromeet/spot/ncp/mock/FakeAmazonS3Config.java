package org.depromeet.spot.ncp.mock;

import org.depromeet.spot.ncp.config.ObjectStorageConfig;
import org.depromeet.spot.ncp.property.ObjectStorageProperties;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.services.s3.AmazonS3;

@Configuration
public class FakeAmazonS3Config extends ObjectStorageConfig {

    public FakeAmazonS3Config(ObjectStorageProperties objectStorageProperties) {
        super(objectStorageProperties);
    }

    @Bean
    @Primary
    @Override
    public AmazonS3 getAmazonS3() {
        return Mockito.mock(AmazonS3.class);
    }
}
