package org.depromeet.spot.infrastructure.jpa.review.entity.scrap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.scrap.ReviewScrap;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review_scraps")
public class ReviewScrapEntity extends BaseEntity {
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    public static ReviewScrapEntity from(ReviewScrap scrap) {
        return new ReviewScrapEntity(scrap.getMemberId(), scrap.getReviewId());
    }

    public ReviewScrap toDomain() {
        return ReviewScrap.builder().id(this.getId()).memberId(memberId).reviewId(reviewId).build();
    }
}
