package org.depromeet.spot.domain.review;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Review {

    private final Long id;
    private final Long userId;
    private final Long blockId;
    private final Long seatId;
    private final Long rowId;
    private final Long seatNumber;
    private final LocalDate date; // 시간은 미표기
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String status;

    public Review(
            Long id,
            Long userId,
            Long blockId,
            Long seatId,
            Long rowId,
            Long seatNumber,
            LocalDate date,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String status) {
        this.id = id;
        this.userId = userId;
        this.blockId = blockId;
        this.seatId = seatId;
        this.rowId = rowId;
        this.seatNumber = seatNumber;
        this.date = date;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
}
