package org.depromeet.spot.application.common.config;

import org.depromeet.spot.infrastructure.InfrastructureConfig;
import org.depromeet.spot.usecase.config.UsecaseConfig;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = {"org.depromeet.spot.application"})
@ConfigurationPropertiesScan(basePackages = {"org.depromeet.spot.application"})
@Import(value = {UsecaseConfig.class, SwaggerConfig.class, InfrastructureConfig.class})
public class SpotApplicationConfig {}
