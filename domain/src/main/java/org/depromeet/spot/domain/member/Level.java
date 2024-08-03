package org.depromeet.spot.domain.member;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Level {

    private static final int[][] LEVEL_UP_TABLE = {
        {0, 0}, {1, 2}, {3, 4}, {5, 7}, {8, 13}, {14, 20}, {21}
    };

    private final Long id;
    private final int value;
    private final String title;
    private final String mascotImageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public static int calculateLevel(final long reviewCnt) {
        if (reviewCnt >= LEVEL_UP_TABLE[0][1]) {
            return 0;
        } else if (reviewCnt >= LEVEL_UP_TABLE[1][1]) {
            return 1;
        } else if (reviewCnt >= LEVEL_UP_TABLE[2][1]) {
            return 2;
        } else if (reviewCnt >= LEVEL_UP_TABLE[3][1]) {
            return 3;
        } else if (reviewCnt >= LEVEL_UP_TABLE[4][1]) {
            return 4;
        } else if (reviewCnt >= LEVEL_UP_TABLE[5][1]) {
            return 5;
        }
        return 6;
    }

    public static long calculateReviewCntToLevelUp(long reviewCnt) {
        int nextLevelMinimumReview;
        if (reviewCnt == 0) {
            nextLevelMinimumReview = 1;
        } else if (reviewCnt <= 2) {
            nextLevelMinimumReview = 3;
        } else if (2 < reviewCnt && reviewCnt <= 4) {
            nextLevelMinimumReview = 5;
        } else if (4 < reviewCnt && reviewCnt <= 7) {
            nextLevelMinimumReview = 8;
        } else if (7 < reviewCnt && reviewCnt <= 13) {
            nextLevelMinimumReview = 14;
        } else if (13 < reviewCnt && reviewCnt <= 20) {
            nextLevelMinimumReview = 21;
        } else return 0;
        return nextLevelMinimumReview - reviewCnt;
    }

    public int getMinimum() {
        return LEVEL_UP_TABLE[value][0];
    }

    public Integer getMaximum() {
        if (LEVEL_UP_TABLE[value].length == 1) return null;
        return LEVEL_UP_TABLE[value][1];
    }
}
