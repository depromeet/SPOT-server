package org.depromeet.spot.usecase.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MixpanelEvent {

    private final MixpanelEventName mixpanelEventName;

    private final String distinctId;

    @Getter
    public enum MixpanelEventName {
        REVIEW_REGISTER("review_register"),
        REVIEW_OPEN_COUNT("review_open_count"),
        REVIEW_LIKE_COUNT("review_like_count"),
        REVIEW_SCRAP_COUNT("review_scrap_count"),
        ;

        String value;

        MixpanelEventName(String value) {
            this.value = value;
        }
    }
}
