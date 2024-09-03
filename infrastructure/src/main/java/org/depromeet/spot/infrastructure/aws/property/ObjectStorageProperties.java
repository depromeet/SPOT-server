package org.depromeet.spot.infrastructure.aws.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ConfigurationProperties(prefix = "aws.s3")
public class ObjectStorageProperties {
    String accessKey;
    String secretKey;
    String bucketName;
    String basicProfileImageUrl;
}
