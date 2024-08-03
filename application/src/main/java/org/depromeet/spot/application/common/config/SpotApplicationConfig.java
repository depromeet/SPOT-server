package org.depromeet.spot.application.common.config;

import org.depromeet.spot.infrastructure.aws.AwsConfig;
import org.depromeet.spot.infrastructure.jpa.config.JpaConfig;
import org.depromeet.spot.usecase.config.UsecaseConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {"org.depromeet.spot.application"})
@Configuration
@Import(value = {UsecaseConfig.class, JpaConfig.class, AwsConfig.class, SwaggerConfig.class})
public class SpotApplicationConfig {}
