package org.depromeet.spot.infrastructure.aws.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ncp.object-storage")
public record ObjectStorageProperties(String accessKey, String secretKey) {}
