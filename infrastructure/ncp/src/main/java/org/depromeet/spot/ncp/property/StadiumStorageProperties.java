package org.depromeet.spot.ncp.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

// FIXME: ncp 세팅 완료 후, applicaiton.yml 참고해서 prefix 추가
@ConfigurationProperties(prefix = "stadium-storage")
public record StadiumStorageProperties(String bucketName, String folderName) {}
