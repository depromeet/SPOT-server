package org.depromeet.spot.jpa.review.repository;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewJpaRepository reviewJpaRepository;
    private final ReviewCustomRepository reviewCustomRepository;

    @Override
    public Page<Review> findByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            Pageable pageable) {
        return reviewCustomRepository.findByStadiumIdAndBlockCode(
                stadiumId, blockCode, rowNumber, seatNumber, year, month, pageable);
    }

    @Override
    public Page<Review> findByUserId(Long userId, Integer year, Integer month, Pageable pageable) {
        return reviewCustomRepository.findByUserId(userId, year, month, pageable);
    }

    @Override
    public List<ReviewYearMonth> findReviewMonthsByMemberId(Long memberId) {
        return reviewCustomRepository.findReviewMonthsByMemberId(memberId);
    }

    @Override
    public Review save(Review review) {
        ReviewEntity savedEntity = reviewJpaRepository.save(ReviewEntity.from(review));
        return savedEntity.toDomain();
    }

    @Override
    public long countByUserId(Long memberId) {
        return reviewJpaRepository.countByMemberId(memberId);
    }
}
