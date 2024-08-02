package org.depromeet.spot.domain.member;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Level {

    private final Long id;
    private final int value;
    private final String title;
    private final String mascotImageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public static int calculateLevel(final long reviewCnt) {
        if (reviewCnt == 0) {
            return 0;
        }
        if (reviewCnt <= 2) {
            return 1;
        }
        if (2 < reviewCnt && reviewCnt <= 4) {
            return 2;
        }
        if (4 < reviewCnt && reviewCnt <= 7) {
            return 3;
        }
        if (7 < reviewCnt && reviewCnt <= 13) {
            return 4;
        }
        if (13 < reviewCnt && reviewCnt <= 20) {
            return 5;
        }
        return 6;
    }

    public static long calculateReviewCntToLevelUp(long reviewCnt) {
        int nextLevel;
        if (reviewCnt == 0) {
            nextLevel = 1;
        } else if (reviewCnt <= 2) {
            nextLevel = 3;
        } else if (2 < reviewCnt && reviewCnt <= 4) {
            nextLevel = 5;
        } else if (4 < reviewCnt && reviewCnt <= 7) {
            nextLevel = 8;
        } else if (7 < reviewCnt && reviewCnt <= 13) {
            nextLevel = 14;
        } else if (13 < reviewCnt && reviewCnt <= 20) {
            nextLevel = 21;
        } else return 0;
        return nextLevel - reviewCnt;
    }
}
