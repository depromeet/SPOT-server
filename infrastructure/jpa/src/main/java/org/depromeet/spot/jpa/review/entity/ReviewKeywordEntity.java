package org.depromeet.spot.jpa.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.ReviewKeyword;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_keywords")
@NoArgsConstructor
@AllArgsConstructor
public class ReviewKeywordEntity extends BaseEntity {
    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    @Column(name = "is_positive", nullable = false)
    private Boolean isPositive;

    public static ReviewKeywordEntity from(ReviewKeyword reviewKeyword) {
        return new ReviewKeywordEntity(
                reviewKeyword.getReviewId(),
                reviewKeyword.getKeywordId(),
                reviewKeyword.getIsPositive());
    }

    public ReviewKeyword toDomain() {
        return new ReviewKeyword(this.getId(), reviewId, keywordId, isPositive);
    }
}
