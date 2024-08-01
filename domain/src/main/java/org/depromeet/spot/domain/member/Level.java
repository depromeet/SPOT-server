package org.depromeet.spot.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Level {

    private final Long id;
    private final int value;
    private final String title;
    private final String mascotImageUrl;

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
}
