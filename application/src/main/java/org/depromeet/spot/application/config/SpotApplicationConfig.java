package org.depromeet.spot.application.config;

import org.depromeet.spot.jpa.config.JpaConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {"org.depromeet.spot.application"})
@Configuration
@Import({JpaConfig.class})
public class SpotApplicationConfig {}
