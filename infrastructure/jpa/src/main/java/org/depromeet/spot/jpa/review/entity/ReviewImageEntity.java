package org.depromeet.spot.jpa.review.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.ReviewImage;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_images")
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImageEntity extends BaseEntity {

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Column(name = "deleted_at", nullable = false, length = 15)
    private LocalDateTime deletedAt;

    public static ReviewImageEntity from(ReviewImage reviewImage) {
        return new ReviewImageEntity(
                reviewImage.getReviewId(), reviewImage.getUrl(), reviewImage.getDeletedAt());
    }

    public ReviewImage toDomain() {
        return ReviewImage.builder()
                .id(this.getId())
                .reviewId(reviewId)
                .url(url)
                .createdAt(this.getCreatedAt())
                .deletedAt(deletedAt)
                .build();
    }
}
