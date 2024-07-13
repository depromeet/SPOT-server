package org.depromeet.spot.domain.review;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Review {

    private final Long id;
    private final Long userId;
    private final Long blockId;
    private final Long seatId;
    private final Long rowId;
    private final Long seatNumber;
    private final LocalDateTime date; // 시간은 미표기
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
}
