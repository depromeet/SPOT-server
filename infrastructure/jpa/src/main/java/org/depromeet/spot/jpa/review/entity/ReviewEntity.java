package org.depromeet.spot.jpa.review.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "stadium_id", nullable = false)
    private Long stadiumId;

    @Column(name = "block_id", nullable = false)
    private Long blockId;

    @Column(name = "row_id", nullable = false)
    private Long rowId;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Column(name = "seat_number", nullable = false)
    private Long seatNumber;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "content", length = 300)
    private String content;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static Review createReviewWithDetails(
            ReviewEntity entity,
            List<ReviewImageEntity> images,
            List<ReviewKeywordEntity> keywords) {
        return Review.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .stadiumId(entity.getStadiumId())
                .blockId(entity.getBlockId())
                .rowId(entity.getRowId())
                .seatId(entity.getSeatId())
                .seatNumber(entity.getSeatNumber())
                .dateTime(entity.getDateTime())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .images(
                        images.stream()
                                .map(ReviewImageEntity::toDomain)
                                .collect(Collectors.toList()))
                .keywords(
                        keywords.stream()
                                .map(ReviewKeywordEntity::toDomain)
                                .collect(Collectors.toList()))
                .build();
    }

    public static ReviewEntity from(Review review) {
        return new ReviewEntity(
                review.getUserId(),
                review.getStadiumId(),
                review.getBlockId(),
                review.getRowId(),
                review.getSeatId(),
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
                .seatId(seatId)
                .seatNumber(seatNumber)
                .dateTime(dateTime)
                .content(content)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .deletedAt(deletedAt)
                .build();
    }
}
