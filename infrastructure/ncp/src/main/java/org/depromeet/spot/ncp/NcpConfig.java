package org.depromeet.spot.ncp;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = {"org.depromeet.spot.ncp.property"})
@ComponentScan(basePackages = {"org.depromeet.spot.ncp"})
public class NcpConfig {}
