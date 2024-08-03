package org.depromeet.spot.infrastructure.jpa.review.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.infrastructure.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.LocationInfo;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public Review save(Review review) {
        ReviewEntity entity = ReviewEntity.from(review);
        ReviewEntity savedEntity = reviewJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewJpaRepository.findById(id).map(ReviewEntity::toDomain);
    }

    @Override
    public long countByUserId(Long id) {
        return reviewJpaRepository.countByMemberId(id);
    }

    @Override
    public Page<Review> findByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            Pageable pageable) {
        Page<ReviewEntity> reviewEntities =
                reviewJpaRepository.findByStadiumIdAndBlockCode(
                        stadiumId, blockCode, rowNumber, seatNumber, year, month, pageable);
        return reviewEntities.map(ReviewEntity::toDomain);
    }

    @Override
    public Page<Review> findByUserId(Long userId, Integer year, Integer month, Pageable pageable) {
        Page<ReviewEntity> reviewEntities =
                reviewJpaRepository.findByUserId(userId, year, month, pageable);
        return reviewEntities.map(ReviewEntity::toDomain);
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
        return reviewJpaRepository.findLocationInfoByStadiumIdAndBlockCode(stadiumId, blockCode);
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
}
