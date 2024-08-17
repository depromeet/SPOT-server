package org.depromeet.spot.infrastructure.jpa.review.entity.like;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.like.ReviewLike;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review_likes")
public class ReviewLikeEntity extends BaseEntity {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    public static ReviewLikeEntity from(ReviewLike like) {
        return new ReviewLikeEntity(like.getMemberId(), like.getReviewId());
    }

    public ReviewLike toDomain() {
        return ReviewLike.builder().id(this.getId()).memberId(memberId).reviewId(reviewId).build();
    }
}
