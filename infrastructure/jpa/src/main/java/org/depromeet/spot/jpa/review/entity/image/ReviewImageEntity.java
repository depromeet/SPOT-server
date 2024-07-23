package org.depromeet.spot.jpa.review.entity.image;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.image.ReviewImage;
import org.depromeet.spot.jpa.common.entity.BaseEntity;
import org.depromeet.spot.jpa.review.entity.ReviewEntity;

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

    public ReviewImage toDomain() {
        return ReviewImage.builder().id(this.getId()).url(this.url).build();
    }

    public static ReviewImageEntity from(ReviewImage reviewImage, ReviewEntity review) {
        ReviewImageEntity entity = new ReviewImageEntity();
        entity.setId(reviewImage.getId());
        entity.review = review; // 여기서 review 설정
        entity.url = reviewImage.getUrl();
        return entity;
    }

    public void setReview(ReviewEntity entity) {
        this.review = entity;
    }

    //    public static ReviewImageEntity from(ReviewImage reviewImage) {
    //        ReviewImageEntity entity = new ReviewImageEntity();
    //        entity.setId(reviewImage.getId());
    //        entity.setReview(reviewImage.getReview());
    //        entity.url = reviewImage.getUrl();
    //        return entity;
    //    }

    //    public void setReview(Review review){
    //        this.review=review
    //    }

    public static ReviewImageEntity withReviewImage(ReviewImage reviewImage) {
        return new ReviewImageEntity(reviewImage);
    }

    public ReviewImageEntity(ReviewImage reviewImage) {
        super(reviewImage.getId(), null, null, null);
        review = ReviewEntity.withReview(reviewImage.getReview());
        url = reviewImage.getUrl();
    }

    //    public static ReviewImageEntity from(ReviewImage reviewImage, ReviewEntity review) {
    //        ReviewImageEntity entity = new ReviewImageEntity();
    //        entity.setReview(review);
    //        entity.setUrl(reviewImage.getUrl());
    //        return entity;
    //    }
    //
    //    public void setUrl(String url) {
    //        this.url = url;
    //    }
    //
    //    public void setReview(ReviewEntity review) {
    //        this.review = review;
    //    }
    //
    //    public ReviewImage toDomain() {
    //        return ReviewImage.builder()
    //                .id(this.getId())
    //                .reviewId(review != null ? review.getId() : null)
    //                .url(url)
    //                .build();
    //    }
}
