package org.depromeet.spot.jpa.review.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "keyword_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private KeywordEntity keyword;

    public static ReviewKeywordEntity from(ReviewKeyword reviewKeyword) {
        return new ReviewKeywordEntity(
                ReviewEntity.from(reviewKeyword.getReview()),
                KeywordEntity.from(reviewKeyword.getKeyword()));
    }

    public ReviewKeyword toDomain() {
        return ReviewKeyword.builder()
                .id(this.getId())
                .review(review.toDomain())
                .keyword(keyword.toDomain())
                .build();
    }
}
