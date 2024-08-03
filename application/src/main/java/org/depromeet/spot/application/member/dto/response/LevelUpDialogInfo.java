package org.depromeet.spot.application.member.dto.response;

import org.depromeet.spot.domain.member.Level;

public record LevelUpDialogInfo(String title, String levelUpImage) {

    public static LevelUpDialogInfo from(Level level) {
        return new LevelUpDialogInfo(level.getTitle(), level.getLevelUpImageUrl());
    }
}
