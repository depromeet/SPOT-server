package org.depromeet.spot.ncp.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ncp.object-storage")
public record ObjectStorageProperties(String accessKey, String secretKey) {}
