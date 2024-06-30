package org.depromeet.spot.application.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(
    basePackages = {
        "org.depromeet.spot.application"
    }
)
@Configuration
public class SpotApplicationConfig {

}

