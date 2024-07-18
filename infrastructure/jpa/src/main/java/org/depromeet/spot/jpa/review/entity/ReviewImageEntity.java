package org.depromeet.spot.jpa.review.entity;

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

    public static ReviewImageEntity from(ReviewImage reviewImage) {
        return new ReviewImageEntity(reviewImage.getReviewId(), reviewImage.getUrl());
    }

    public ReviewImage toDomain() {
        return ReviewImage.builder()
                .id(this.getId())
                .reviewId(reviewId)
                .url(url)
                .createdAt(this.getCreatedAt())
                .build();
    }
}
