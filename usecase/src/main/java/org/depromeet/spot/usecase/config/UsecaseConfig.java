package org.depromeet.spot.usecase.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {
            "org.depromeet.spot.usecase.port",
            "org.depromeet.spot.usecase.service",
        })
public class UsecaseConfig {}
