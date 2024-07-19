package org.depromeet.spot.jpa.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_images")
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImageEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "review_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ReviewEntity review;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    //    public static ReviewImageEntity from(ReviewImage reviewImage) {
    //        return new ReviewImageEntity(reviewImage.getReviewId(), reviewImage.getUrl());
    //    }
    //
    //    public ReviewImage toDomain() {
    //        return ReviewImage.builder()
    //                .id(this.getId())
    //                .reviewId(reviewId)
    //                .url(url)
    //                .createdAt(this.getCreatedAt())
    //                .build();
    //    }
}
