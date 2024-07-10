package org.depromeet.spot.jpa.review.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.ReviewImage;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_images")
@NoArgsConstructor
public class ReviewImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    public ReviewImageEntity(
            Long id, Long reviewId, String url, LocalDateTime createdAt, String status) {
        this.id = id;
        this.reviewId = reviewId;
        this.url = url;
        this.createdAt = createdAt;
        this.status = status;
    }

    public static ReviewImageEntity from(ReviewImage reviewImage) {
        return new ReviewImageEntity(
                reviewImage.getId(),
                reviewImage.getReviewId(),
                reviewImage.getUrl(),
                reviewImage.getCreatedAt(),
                reviewImage.getStatus());
    }

    public ReviewImage toDomain() {
        return new ReviewImage(id, reviewId, url, createdAt, status);
    }
}
