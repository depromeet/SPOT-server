package org.depromeet.spot.infrastructure.aws.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.DependsOn;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ConfigurationProperties(prefix = "aws.s3")
@DependsOn("awsSecretsManagerConfig")
public class ObjectStorageProperties {
    String accessKey;
    String secretKey;
    String bucketName;
    String basicProfileImageUrl;
}
