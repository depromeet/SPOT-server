package org.depromeet.spot.infrastructure.jpa.config;

import jakarta.annotation.PostConstruct;

import org.depromeet.spot.infrastructure.jpa.common.P6spySqlFormatter;
import org.springframework.context.annotation.Configuration;

import com.p6spy.engine.spy.P6SpyOptions;

@Configuration
public class P6spyConfig {

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatter.class.getName());
    }
}
