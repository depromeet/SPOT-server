package org.depromeet.spot.application.member.dto.response;

import org.depromeet.spot.domain.member.Level;

import lombok.Builder;

@Builder
public record LevelUpTableResponse(Integer level, String title, int minimum, Integer maximum) {
    public static LevelUpTableResponse from(Level level) {
        LevelUpTableResponse levelUpTableResponse =
                LevelUpTableResponse.builder()
                        .level(level.getValue())
                        .title(level.getTitle())
                        .minimum(level.getMinimum())
                        .maximum(level.getMaximum())
                        .build();

        return levelUpTableResponse;
    }
}
