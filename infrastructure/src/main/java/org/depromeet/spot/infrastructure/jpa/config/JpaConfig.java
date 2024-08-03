package org.depromeet.spot.infrastructure.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"org.depromeet.spot.infrastructure.jpa"})
@EnableJpaRepositories(basePackages = {"org.depromeet.spot.infrastructure.jpa"})
@EntityScan(basePackages = {"org.depromeet.spot.infrastructure.jpa"})
public class JpaConfig {}
