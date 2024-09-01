package org.depromeet.spot.infrastructure.mixpanel.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mixpanel")
public record MixpanelProperties(String token) {}
