package org.depromeet.spot.application.mixpanel;

import lombok.Getter;

@Getter
public enum MixpanelEvent {
    REVIEW_REGISTER("review_register"),
    REVIEW_REGISTER_MAX("review_register"),
    REVIEW_OPEN_COUNT("review_open_count"),
    REVIEW_LIKE_COUNT("review_like_count"),
    REVIEW_SCRAP_COUNT("review_scrap_count"),
    ;

    String value;

    MixpanelEvent(String value) {
        this.value = value;
    }
}
