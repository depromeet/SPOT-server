package org.depromeet.spot.infrastructure.aws;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = {"org.depromeet.spot.infrastructure.aws.property"})
@ComponentScan(basePackages = {"org.depromeet.spot.ncp"})
public class NcpConfig {}
