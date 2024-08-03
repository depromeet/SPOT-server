package org.depromeet.spot.domain.member;

import java.time.LocalDateTime;

import com.google.common.collect.ImmutableMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Level {

    private static final ImmutableMap<Integer, Integer> LEVEL_MINIMUM_CONDITIONS =
            ImmutableMap.<Integer, Integer>builder()
                    .put(0, 0)
                    .put(1, 1)
                    .put(2, 3)
                    .put(3, 5)
                    .put(4, 8)
                    .put(5, 14)
                    .put(6, 21)
                    .build();

    private static final ImmutableMap<Integer, Integer> LEVEL_MAXIMUM_CONDITIONS =
            ImmutableMap.<Integer, Integer>builder()
                    .put(0, 0)
                    .put(1, 2)
                    .put(2, 4)
                    .put(3, 7)
                    .put(4, 13)
                    .put(5, 20)
                    .build();
    private final Long id;
    private final int value;
    private final String title;
    private final String mascotImageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public static int calculateLevel(final long reviewCnt) {
        if (reviewCnt > LEVEL_MINIMUM_CONDITIONS.get(6)) {
            return 6;
        } else if (reviewCnt > LEVEL_MAXIMUM_CONDITIONS.get(5)) {
            return 5;
        } else if (reviewCnt > LEVEL_MAXIMUM_CONDITIONS.get(4)) {
            return 4;
        } else if (reviewCnt > LEVEL_MAXIMUM_CONDITIONS.get(3)) {
            return 3;
        } else if (reviewCnt > LEVEL_MAXIMUM_CONDITIONS.get(2)) {
            return 2;
        } else if (reviewCnt > LEVEL_MAXIMUM_CONDITIONS.get(1)) {
            return 1;
        }
        return 0;
    }

    public static long calculateReviewCntToLevelUp(long reviewCnt) {
        int level = calculateLevel(reviewCnt);
        if (level > 5) {
            return 0;
        }
        // (다음 레벨의 최소 리뷰 조건) - (현재 작성한 리뷰 수)
        return LEVEL_MINIMUM_CONDITIONS.get(level + 1) - reviewCnt;
    }

    public int getMinimum() {
        return LEVEL_MINIMUM_CONDITIONS.get(value);
    }

    public Integer getMaximum() {
        if (value > 5) return null;
        return LEVEL_MAXIMUM_CONDITIONS.get(value);
    }
}
