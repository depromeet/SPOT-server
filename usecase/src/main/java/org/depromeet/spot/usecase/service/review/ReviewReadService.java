package org.depromeet.spot.usecase.service.review;

import java.util.List;

import org.depromeet.spot.domain.review.MyReviewListResult;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewImage;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.domain.review.result.BlockReviewListResult;
import org.depromeet.spot.domain.review.result.KeywordCount;
import org.depromeet.spot.usecase.port.in.review.ReviewReadUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewKeywordRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewReadService implements ReviewReadUsecase {
    private final ReviewRepository reviewRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final ReviewImageRepository reviewImageRepository;

    private static final int TOP_KEYWORDS_LIMIT = 5;
    private static final int TOP_IMAGES_LIMIT = 5;

    @Override
    public BlockReviewListResult findReviewsByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            Integer page,
            Integer size) {

        // stadiumId랑 blockCode로 blockId를 조회 후 이걸 통해 reviews를 조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Review> reviewPage =
                reviewRepository.findByStadiumIdAndBlockCode(
                        stadiumId, blockCode, rowNumber, seatNumber, year, month, pageRequest);

        //  stadiumId랑 blockCode로 blockId를 조회 후 이걸 통해 topKeywords를 조회
        List<KeywordCount> topKeywords =
                reviewKeywordRepository.findTopKeywordsByStadiumIdAndBlockCode(
                        stadiumId, blockCode, TOP_KEYWORDS_LIMIT);

        // stadiumId랑 blockCode로 blockId를 조회 후 이걸 통해 topImages를 조회
        List<ReviewImage> topReviewImages =
                reviewImageRepository.findTopReviewImagesByStadiumIdAndBlockCode(
                        stadiumId, blockCode, TOP_IMAGES_LIMIT);

        return BlockReviewListResult.builder()
                .reviews(reviewPage.getContent())
                .topKeywords(topKeywords)
                .topReviewImages(topReviewImages)
                .totalElements(reviewPage.getTotalElements())
                .totalPages(reviewPage.getTotalPages())
                .number(reviewPage.getNumber())
                .size(reviewPage.getSize())
                .build();
    }

    @Override
    public MyReviewListResult findMyReviewsByUserId(
            Long userId, Integer year, Integer month, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Review> reviewPage = reviewRepository.findByUserId(userId, year, month, pageRequest);

        return MyReviewListResult.builder()
                .reviews(reviewPage.getContent())
                .totalElements(reviewPage.getTotalElements())
                .totalPages(reviewPage.getTotalPages())
                .number(reviewPage.getNumber())
                .size(reviewPage.getSize())
                .build();
    }

    @Override
    public List<ReviewYearMonth> findReviewMonths(Long memberId) {
        return reviewRepository.findReviewMonthsByMemberId(memberId);
    }
}
