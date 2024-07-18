package org.depromeet.spot.jpa.review.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.jpa.review.entity.ReviewImageEntity;
import org.depromeet.spot.jpa.review.entity.ReviewKeywordEntity;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewCustomRepository reviewCustomRepository;
    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public List<Review> findByBlockId(
            Long stadiumId, Long blockId, Long rowId, Long seatNumber, int offset, int limit) {
        List<ReviewEntity> reviews =
                reviewCustomRepository.findByBlockIdWithFilters(
                        stadiumId, blockId, rowId, seatNumber, offset, limit);
        return reviews.stream().map(this::fetchReviewDetails).collect(Collectors.toList());
    }

    @Override
    public List<Review> findByUserId(
            Long userId, int offset, int limit, Integer year, Integer month) {
        List<ReviewEntity> reviews =
                reviewCustomRepository.findByUserIdWithFilters(userId, offset, limit, year, month);
        return reviews.stream().map(this::fetchReviewDetails).collect(Collectors.toList());
    }

    @Override
    public Long countByBlockId(Long stadiumId, Long blockId, Long rowId, Long seatNumber) {
        return reviewCustomRepository.countByBlockIdWithFilters(
                stadiumId, blockId, rowId, seatNumber);
    }

    @Override
    public Long countByUserId(Long userId, Integer year, Integer month) {
        return reviewCustomRepository.countByUserIdWithFilters(userId, year, month);
    }

    @Override
    public long countByUserId(Long userId) {
        return reviewJpaRepository.countByUserId(userId);
    }

    @Override
    public List<KeywordCount> findTopKeywordsByBlockId(Long stadiumId, Long blockId, int limit) {
        return reviewCustomRepository.findTopKeywordsByBlockId(stadiumId, blockId, limit);
    }

    @Override
    public Review save(Review review) {
        ReviewEntity entity = reviewJpaRepository.save(ReviewEntity.from(review));
        return entity.toDomain();
    }

    private Review fetchReviewDetails(ReviewEntity reviewEntity) {
        List<ReviewImageEntity> images =
                reviewCustomRepository.findImagesByReviewIds(List.of(reviewEntity.getId()));
        List<ReviewKeywordEntity> keywords =
                reviewCustomRepository.findKeywordsByReviewIds(List.of(reviewEntity.getId()));

        return ReviewEntity.createReviewWithDetails(reviewEntity, images, keywords);
    }
}
