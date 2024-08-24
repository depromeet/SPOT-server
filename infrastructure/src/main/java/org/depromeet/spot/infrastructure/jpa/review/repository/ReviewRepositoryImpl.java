package org.depromeet.spot.infrastructure.jpa.review.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.common.exception.review.ReviewException.ReviewNotFoundException;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.ReviewType;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.infrastructure.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.LocationInfo;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;
    private final ReviewCustomRepository reviewCustomRepository;

    @Override
    public void updateLikesCount(Long reviewId, int likesCount) {
        reviewJpaRepository.updateLikesCount(reviewId, likesCount);
    }

    @Override
    public void updateScrapsCount(Long reviewId, int scrapsCount) {
        reviewJpaRepository.updateScrapsCount(reviewId, scrapsCount);
    }

    @Override
    public Review save(Review review) {
        ReviewEntity entity = ReviewEntity.from(review);
        ReviewEntity savedEntity = reviewJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Review findById(Long id) {
        ReviewEntity entity =
                reviewJpaRepository
                        .findById(id)
                        .orElseThrow(() -> new ReviewNotFoundException("요청한 리뷰를 찾을 수 없습니다." + id));
        return entity.toDomain();
    }

    @Override
    public long countByUserId(Long id) {
        return reviewJpaRepository.countByMemberIdAndDeletedAtIsNull(id);
    }

    @Override
    public List<Review> findByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size) {
        List<ReviewEntity> reviewEntities =
                reviewCustomRepository.findByStadiumIdAndBlockCode(
                        stadiumId,
                        blockCode,
                        rowNumber,
                        seatNumber,
                        year,
                        month,
                        cursor,
                        sortBy,
                        size,
                        ReviewType.VIEW);
        return reviewEntities.stream().map(ReviewEntity::toDomain).toList();
    }

    @Override
    public List<Review> findAllByUserId(
            Long userId,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size) {
        List<ReviewEntity> reviewEntities =
                reviewCustomRepository.findAllByUserId(userId, year, month, cursor, sortBy, size);
        return reviewEntities.stream().map(ReviewEntity::toDomain).toList();
    }

    @Override
    public List<ReviewYearMonth> findReviewMonthsByMemberId(Long memberId) {
        return reviewJpaRepository.findReviewMonthsByMemberId(memberId);
    }

    @Override
    public Long softDeleteByIdAndMemberId(Long reviewId, Long memberId) {
        int updatedCount =
                reviewJpaRepository.softDeleteByIdAndMemberId(
                        reviewId, memberId, LocalDateTime.now());
        if (updatedCount == 0) {
            throw new IllegalArgumentException("Review not found or not owned by the member");
        }
        return reviewId;
    }

    @Override
    public LocationInfo findLocationInfoByStadiumIdAndBlockCode(Long stadiumId, String blockCode) {
        return reviewCustomRepository.findLocationInfoByStadiumIdAndBlockCode(stadiumId, blockCode);
    }

    @Override
    public Review findLastReviewByMemberId(Long memberId) {
        ReviewEntity reviewEntity = reviewJpaRepository.findLastReviewByMemberId(memberId);
        return reviewEntity.toDomain();
    }

    @Override
    public long countByIdByMemberId(Long memberId) {
        return reviewJpaRepository.countByIdByMemberId(memberId);
    }

    @Override
    public long countByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month) {
        return reviewCustomRepository.countByStadiumIdAndBlockCode(
                stadiumId, blockCode, rowNumber, seatNumber, year, month);
    }
}
