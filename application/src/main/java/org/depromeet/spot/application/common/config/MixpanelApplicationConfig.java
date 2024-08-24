package org.depromeet.spot.application.common.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MixpanelApplicationConfig {

    private final Mixpanel mixpanel = new Mixpanel();

    public Mixpanel getMixpanel() {
        return this.mixpanel;
    }

    public static class Mixpanel {
        private String token;

        public Mixpanel() {}

        public String getToken() {
            return token;
        }

        public void setToken(final String token) {
            this.token = token;
        }
    }
}
