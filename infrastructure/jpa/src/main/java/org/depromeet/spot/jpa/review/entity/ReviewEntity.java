package org.depromeet.spot.jpa.review.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "block_id", nullable = false)
    private Long blockId;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Column(name = "row_id", nullable = false)
    private Long rowId;

    @Column(name = "seat_number", nullable = false)
    private Long seatNumber;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "content", length = 300)
    private String content;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    public static ReviewEntity from(Review review) {
        return new ReviewEntity(
                review.getUserId(),
                review.getBlockId(),
                review.getSeatId(),
                review.getRowId(),
                review.getSeatNumber(),
                review.getDateTime(),
                review.getContent(),
                review.getStatus());
    }

    public Review toDomain() {
        return new Review(
                this.getId(),
                userId,
                blockId,
                seatId,
                rowId,
                seatNumber,
                dateTime,
                content,
                this.getCreatedAt(),
                this.getUpdatedAt(),
                status);
    }
}
