package org.depromeet.spot.jpa.review.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.Keyword;
import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.jpa.review.entity.KeywordEntity;
import org.depromeet.spot.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewCustomRepository reviewCustomRepository;
    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public List<Review> findByBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            int offset,
            int limit) {
        return reviewCustomRepository.findByBlockCode(
                stadiumId, blockCode, rowNumber, seatNumber, offset, limit);
    }

    @Override
    public List<Review> findByUserId(
            Long userId, int offset, int limit, Integer year, Integer month) {
        List<ReviewEntity> reviews =
                reviewCustomRepository.findByUserIdWithFilters(userId, offset, limit, year, month);
        return reviews.stream().map(this::fetchReviewDetails).collect(Collectors.toList());
    }

    @Override
    public Long countByBlockCode(
            Long stadiumId, String blockCode, Integer rowNumber, Integer seatNumber) {
        return reviewCustomRepository.countByBlockIdWithFilters(
                stadiumId, blockCode, rowNumber, seatNumber);
    }

    @Override
    public Long countByUserId(Long userId, Integer year, Integer month) {
        return reviewCustomRepository.countByUserIdWithFilters(userId, year, month);
    }

    @Override
    public List<KeywordCount> findTopKeywordsByBlockCode(
            Long stadiumId, String blockCode, int limit) {
        return reviewCustomRepository.findTopKeywordsByBlockCode(stadiumId, blockCode, limit);
    }

    @Override
    public Review save(Review review) {
        ReviewEntity entity = reviewJpaRepository.save(ReviewEntity.from(review));
        return entity.toDomain();
    }

    @Override
    public List<Keyword> findKeywordsByReviewIds(List<Long> reviewIds) {
        List<KeywordEntity> keywordEntities =
                reviewCustomRepository.findKeywordsByReviewIds(reviewIds);
        return keywordEntities.stream().map(KeywordEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<ReviewYearMonth> findReviewMonthsByMemberId(Long memberId) {
        return reviewCustomRepository.findReviewMonthsByMemberId(memberId);
    }

    private Review fetchReviewDetails(ReviewEntity reviewEntity) {
        return ReviewEntity.createReviewWithDetails(
                reviewEntity,
                reviewCustomRepository.findImagesByReviewIds(List.of(reviewEntity.getId())),
                reviewCustomRepository.findKeywordsByReviewIds(List.of(reviewEntity.getId())));
    }
}
