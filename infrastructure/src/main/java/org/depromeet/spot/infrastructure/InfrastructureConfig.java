package org.depromeet.spot.infrastructure;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = {"org.depromeet.spot.infrastructure"})
@ComponentScan(basePackages = {"org.depromeet.spot.infrastructure"})
public class InfrastructureConfig {}
