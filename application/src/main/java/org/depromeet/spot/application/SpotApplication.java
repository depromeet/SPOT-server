package org.depromeet.spot.application;

import java.util.TimeZone;

import jakarta.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpotApplication {

    private static final String DEFAULT_TIMEZONE_KST = "Asia/Seoul";

    public static void main(String[] args) {
        SpringApplication.run(SpotApplication.class);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(DEFAULT_TIMEZONE_KST));
    }
}
