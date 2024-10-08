package org.depromeet.spot.usecase.service.review;

import java.util.Map;

import org.depromeet.spot.common.exception.review.ReviewException.UnauthorizedReviewModificationException;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.depromeet.spot.usecase.service.review.processor.ReadReviewProcessor;
import org.depromeet.spot.usecase.service.review.processor.ReviewCreationProcessor;
import org.depromeet.spot.usecase.service.review.processor.ReviewDataProcessor;
import org.depromeet.spot.usecase.service.review.processor.ReviewKeywordProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Service
@RequiredArgsConstructor
@Transactional
public class UpdateReviewService implements UpdateReviewUsecase {

    private final ReviewRepository reviewRepository;
    private final ReviewDataProcessor reviewDataProcessor;
    private final ReviewKeywordProcessor reviewKeywordProcessor;
    private final ReviewCreationProcessor reviewCreationProcessor;
    private final ReadReviewProcessor readReviewProcessor;

    public UpdateReviewResult updateReview(
            Long memberId, Long reviewId, UpdateReviewCommand command) {
        // 1. review id로 조회
        Review existingReview = reviewRepository.findById(reviewId);
        if (!existingReview.getMember().getId().equals(memberId)) {
            throw new UnauthorizedReviewModificationException();
        }

        // 2. 새로운 Review 객체 생성
        Review updatedReview = reviewCreationProcessor.updateReviewData(existingReview, command);

        // keyword와 image 처리
        Map<Long, Keyword> keywordMap =
                reviewCreationProcessor.processReviewDetails(updatedReview, command);

        // 저장 및 blockTopKeyword 업데이트
        Review savedReview = reviewRepository.save(updatedReview);
        reviewKeywordProcessor.updateBlockTopKeywords(existingReview, savedReview);
        savedReview.setKeywordMap(keywordMap);

        // 유저의 리뷰 좋아요, 스크랩 여부
        readReviewProcessor.setLikedAndScrappedStatus(savedReview, memberId);

        return new UpdateReviewResult(savedReview);
    }

    @Override
    public void updateLikesCount(Review review) {
        reviewRepository.updateLikesCount(review.getId(), review.getLikesCount());
    }

    @Override
    public void updateScrapsCount(Review review) {
        reviewRepository.updateScrapsCount(review.getId(), review.getScrapsCount());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void updateviewCount(Long reviewId) {
        Review review = reviewRepository.findById(reviewId);
        review.getViewsCount();
        reviewRepository.updateViewCount(reviewId, review.getViewsCount());
    }
}
