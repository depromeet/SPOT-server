package org.depromeet.spot.jpa.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.ReviewKeyword;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_keywords")
@NoArgsConstructor
public class ReviewKeywordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    @Column(name = "is_positive", nullable = false)
    private Boolean isPositive;

    public ReviewKeywordEntity(Long id, Long reviewId, Long keywordId, Boolean isPositive) {
        this.id = id;
        this.reviewId = reviewId;
        this.keywordId = keywordId;
        this.isPositive = isPositive;
    }

    public static ReviewKeywordEntity from(ReviewKeyword reviewKeyword) {
        return new ReviewKeywordEntity(
                reviewKeyword.getId(),
                reviewKeyword.getReviewId(),
                reviewKeyword.getKeywordId(),
                reviewKeyword.getIsPositive());
    }

    public ReviewKeyword toDomain() {
        return new ReviewKeyword(id, reviewId, keywordId, isPositive);
    }
}
