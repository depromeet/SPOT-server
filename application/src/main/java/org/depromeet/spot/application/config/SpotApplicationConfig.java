package org.depromeet.spot.application.config;

import org.depromeet.spot.usecase.config.UsecaseConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {"org.depromeet.spot.application"})
@Configuration
@Import({UsecaseConfig.class})
public class SpotApplicationConfig {}
