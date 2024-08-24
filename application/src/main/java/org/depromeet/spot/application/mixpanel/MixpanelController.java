package org.depromeet.spot.application.mixpanel;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "믹스패널 이벤트")
public class MixpanelController {

    private final MixpanelUtil mixpanelUtil;

    @PostMapping("/trackEvent")
    public String trackEvent(@RequestParam String eventName, @RequestParam String distinctId) {
        log.info("trackEvent");
        mixpanelUtil.track(eventName, distinctId);
        return "Event tracked: " + eventName;
    }
}
