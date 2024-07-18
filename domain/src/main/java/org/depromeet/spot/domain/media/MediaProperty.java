package org.depromeet.spot.domain.media;

import lombok.Getter;

@Getter
public enum MediaProperty {
    REVIEW("review-images"),
    STADIUM("stadium-images"),
    STADIUM_SEAT("stadium-seat-charts"),
    STADIUM_SEAT_LABEL("stadium-seat-label-charts"),
    TEAM_LOGO("team-logos"),
    PROFILE_IMAGE("profile-images"),
    ;

    private final String folderName;

    MediaProperty(final String folderName) {
        this.folderName = folderName;
    }
}
