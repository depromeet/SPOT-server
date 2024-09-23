package org.depromeet.spot.infrastructure.aws.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.s3")
public record ObjectStorageProperties(
        String accessKey, String secretKey, String bucketName, String basicProfileImageUrl) {}
