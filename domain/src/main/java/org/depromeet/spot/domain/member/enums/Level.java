package org.depromeet.spot.domain.member.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.common.exception.member.MemberException.InvalidLevelException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Level {
    LEVEL_1(1, "직관 첫 걸음"),
    LEVEL_2(2, "경기장 탐험가"),
    LEVEL_3(3, "직관의 여유"),
    LEVEL_4(4, "응원 단장"),
    LEVEL_5(5, "야구장 VIP"),
    LEVEL_6(6, "전설의 직관러"),
    ;

    private final int level;
    private final String title;

    private static final Map<Integer, String> levelTitleMap =
            Arrays.stream(Level.values())
                    .collect(Collectors.toMap(info -> info.level, info -> info.title));

    public static String getTitleFrom(final int level) {
        String title = levelTitleMap.get(level);
        if (title == null) {
            throw new InvalidLevelException();
        }
        return title;
    }
}
