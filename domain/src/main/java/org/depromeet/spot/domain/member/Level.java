package org.depromeet.spot.domain.member;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Level {

    // of는 오버로딩으로 파라미터 수에 따라 사용됨. 10개 이상은 오버로딩 없어서 오류가 발생하니 주의!
    private static final Map<Integer, Integer> LEVEL_MINIMUM_CONDITIONS =
            Map.of(
                    0, 0,
                    1, 1,
                    2, 3,
                    3, 5,
                    4, 8,
                    5, 14,
                    6, 21);

    private static final Map<Integer, Integer> LEVEL_MAXIMUM_CONDITIONS =
            Map.of(
                    0, 0,
                    1, 2,
                    2, 4,
                    3, 7,
                    4, 13,
                    5, 20);

    private final Long id;
    private final int value;
    private final String title;
    private final String mascotImageUrl;
    private final String levelUpImageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public static int calculateLevel(final long reviewCnt) {
        if (reviewCnt >= LEVEL_MINIMUM_CONDITIONS.get(6)) {
            return 6;
        } else if (reviewCnt >= LEVEL_MINIMUM_CONDITIONS.get(5)) {
            return 5;
        } else if (reviewCnt >= LEVEL_MINIMUM_CONDITIONS.get(4)) {
            return 4;
        } else if (reviewCnt >= LEVEL_MINIMUM_CONDITIONS.get(3)) {
            return 3;
        } else if (reviewCnt >= LEVEL_MINIMUM_CONDITIONS.get(2)) {
            return 2;
        } else if (reviewCnt >= LEVEL_MINIMUM_CONDITIONS.get(1)) {
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
