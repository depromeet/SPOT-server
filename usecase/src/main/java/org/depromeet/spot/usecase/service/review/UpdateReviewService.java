package org.depromeet.spot.usecase.service.review;

import java.util.Map;

import org.depromeet.spot.common.exception.review.ReviewException.UnauthorizedReviewModificationException;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase;
import org.depromeet.spot.usecase.port.out.block.BlockRepository;
import org.depromeet.spot.usecase.port.out.block.BlockRowRepository;
import org.depromeet.spot.usecase.port.out.review.BlockTopKeywordRepository;
import org.depromeet.spot.usecase.port.out.review.KeywordRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
import org.depromeet.spot.usecase.port.out.section.SectionRepository;
import org.depromeet.spot.usecase.port.out.stadium.StadiumRepository;
import org.depromeet.spot.usecase.service.review.processor.ReviewCreationProcessor;
import org.depromeet.spot.usecase.service.review.processor.ReviewDataProcessor;
import org.depromeet.spot.usecase.service.review.processor.ReviewImageProcessor;
import org.depromeet.spot.usecase.service.review.processor.ReviewKeywordProcessor;
import org.springframework.stereotype.Service;
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

    private final StadiumRepository stadiumRepository;
    private final BlockRepository blockRepository;
    private final SectionRepository sectionRepository;
    private final BlockRowRepository blockRowRepository;
    private final ReviewRepository reviewRepository;
    private final SeatRepository seatRepository;
    private final KeywordRepository keywordRepository;
    private final BlockTopKeywordRepository blockTopKeywordRepository;
    private final ReviewDataProcessor reviewDataProcessor;
    private final ReviewKeywordProcessor reviewKeywordProcessor;
    private final ReviewImageProcessor reviewImageProcessor;
    private final ReviewCreationProcessor reviewCreationProcessor;

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
}
