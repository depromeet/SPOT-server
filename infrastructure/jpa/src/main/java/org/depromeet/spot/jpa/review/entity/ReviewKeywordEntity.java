package org.depromeet.spot.jpa.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "review_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ReviewEntity review;

    @Column(name = "content", nullable = false, length = 50)
    private String content;

    @Column(name = "isPositive", nullable = false)
    private boolean isPositive;

    public static ReviewKeywordEntity from(ReviewKeyword reviewKeyword) {
        return new ReviewKeywordEntity(
                ReviewEntity.from(reviewKeyword.getReview()),
                reviewKeyword.getContent(),
                reviewKeyword.getIsPositive());
    }

    public ReviewKeyword toDomain() {
        return ReviewKeyword.builder()
                .id(this.getId())
                .review(review.toDomain())
                .content(content)
                .isPositive(isPositive)
                .build();
    }
}
