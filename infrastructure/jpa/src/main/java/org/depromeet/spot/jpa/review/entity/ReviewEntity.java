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

    @Column(name = "stadium_id", nullable = false)
    private Long stadiumId;

    @Column(name = "block_id", nullable = false)
    private Long blockId;

    @Column(name = "row_id", nullable = false)
    private Long rowId;

    @Column(name = "seat_number", nullable = false)
    private Long seatNumber;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "content", length = 300)
    private String content;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static ReviewEntity from(Review review) {
        return new ReviewEntity(
                review.getUserId(),
                review.getStadiumId(),
                review.getBlockId(),
                review.getRowId(),
                review.getSeatNumber(),
                review.getDateTime(),
                review.getContent(),
                review.getDeletedAt());
    }

    public Review toDomain() {
        return Review.builder()
                .id(this.getId())
                .userId(userId)
                .stadiumId(stadiumId)
                .blockId(blockId)
                .rowId(rowId)
                .seatNumber(seatNumber)
                .dateTime(dateTime)
                .content(content)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .deletedAt(deletedAt)
                .build();
    }
}
