package org.depromeet.spot.infrastructure.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"org.depromeet.spot.jpa"})
@EnableJpaRepositories(basePackages = {"org.depromeet.spot.jpa"})
@EntityScan(basePackages = {"org.depromeet.spot.jpa"})
public class JpaConfig {}
