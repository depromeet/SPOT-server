package org.depromeet.spot.jpa.review.entity.keyword;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.jpa.common.entity.BaseEntity;
import org.depromeet.spot.jpa.review.entity.ReviewEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_keywords")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewKeywordEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "review_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ReviewEntity review;

    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    public static ReviewKeywordEntity from(ReviewKeyword reviewKeyword, ReviewEntity review) {
        ReviewKeywordEntity entity = new ReviewKeywordEntity();
        entity.review = review;
        entity.keywordId = reviewKeyword.getKeywordId();
        return entity;
    }

    public ReviewKeyword toDomain() {
        return ReviewKeyword.builder().id(this.getId()).keywordId(this.keywordId).build();
    }

    //    public ReviewKeyword toDomain() {
    //        return ReviewKeyword.builder()
    //            .id(this.getId())
    //            .keyword(this.keyword.toDomain())
    //            .build();
    //    }

    //    public static ReviewKeywordEntity from(ReviewKeyword reviewKeyword, ReviewEntity review) {
    //        ReviewKeywordEntity entity = new ReviewKeywordEntity();
    ////        entity.setId(reviewKeyword.getId());
    //        entity.review = review;  // 여기서 review 설정
    //        entity.keyword = KeywordEntity.from(reviewKeyword.getKeyword());
    //        return entity;
    //    }

    //    public static ReviewKeywordEntity withReviewKeyword(ReviewKeyword reviewKeyword){
    //        return new ReviewKeywordEntity(reviewKeyword);
    //    }
    //
    //    public ReviewKeywordEntity(ReviewKeyword reviewKeyword){
    //        super(reviewKeyword.getId(), null, null, null);
    //        review = ReviewEntity.withReview(reviewKeyword.getReview());
    //        keyword = KeywordEntity.withKeyword(reviewKeyword.getKeyword());
    //    }

    //    public void setReview(ReviewEntity review){
    //        this.review=review;
    //    }
    //
    //    public void setKeyword(KeywordEntity keyword){
    //        this.keyword = keyword;
    //    }
    //    public static ReviewKeywordEntity from(ReviewKeyword reviewKeyword, ReviewEntity review,
    // KeywordEntity keyword) {
    //        ReviewKeywordEntity entity = new ReviewKeywordEntity();
    //        entity.setReview(review);
    //        entity.setKeyword(keyword);
    //        return entity;
    //    }
    //
    //    public ReviewKeyword toDomain() {
    //        return ReviewKeyword.builder()
    //                .id(this.getId())
    //                .reviewId(review != null ? review.getId() : null)
    //                .keyword(keyword.toDomain())
    //                .build();
    //    }
    //
    //    public void setReview(ReviewEntity review) {
    //        this.review = review;
    //    }
    //
    //    public void setKeyword(KeywordEntity keyword){
    //        this.keyword = keyword;
    //    }
}
