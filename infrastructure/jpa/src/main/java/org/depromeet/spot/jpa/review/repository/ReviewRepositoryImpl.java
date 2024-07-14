package org.depromeet.spot.jpa.review.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.common.exception.review.ReviewException;
import org.depromeet.spot.common.exception.review.ReviewException.InvalidReviewDataException;
import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewImage;
import org.depromeet.spot.domain.review.ReviewKeyword;
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

    @Override
    public List<Review> findByBlockId(
            Long stadiumId, Long blockId, Long rowId, Long seatNumber, int offset, int limit) {
        List<ReviewEntity> reviews =
                reviewCustomRepository.findByBlockIdWithFilters(
                        stadiumId, blockId, rowId, seatNumber, offset, limit);
        if (reviews.isEmpty()) {
            throw new ReviewException.ReviewNotFoundException(
                    "No review found for blockId:" + blockId);
        }
        return reviews.stream().map(this::fetchReviewDetails).collect(Collectors.toList());
    }

    @Override
    public int countByBlockId(Long stadiumId, Long blockId, Long rowId, Long seatNumber) {
        return (int)
                reviewCustomRepository.countByBlockIdWithFilters(
                        stadiumId, blockId, rowId, seatNumber);
    }

    @Override
    public List<KeywordCount> findTopKeywordsByBlockId(Long stadiumId, Long blockId, int limit) {
        return reviewCustomRepository.findTopKeywordsByBlockId(stadiumId, blockId, limit);
    }

    private Review fetchReviewDetails(ReviewEntity reviewEntity) {
        List<ReviewImage> images =
                reviewCustomRepository.findImagesByReviewId(reviewEntity.getId()).stream()
                        .map(ReviewImageEntity::toDomain)
                        .collect(Collectors.toList());

        List<ReviewKeyword> keywords =
                reviewCustomRepository.findKeywordsByReviewId(reviewEntity.getId()).stream()
                        .map(ReviewKeywordEntity::toDomain)
                        .collect(Collectors.toList());

        Review review = reviewEntity.toDomain();
        if (review == null) {
            throw new InvalidReviewDataException(
                    "Failed to convert entity to domain for reviewId: " + reviewEntity.getId());
        }
        return Review.builder()
                .id(review.getId())
                .userId(review.getUserId())
                .stadiumId(review.getStadiumId())
                .blockId(review.getBlockId())
                .rowId(review.getRowId())
                .seatNumber(review.getSeatNumber())
                .dateTime(review.getDateTime())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .deletedAt(review.getDeletedAt())
                .images(images)
                .keywords(keywords)
                .build();
    }
}
