package org.depromeet.spot.infrastructure.jpa.review.repository.image;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.image.TopReviewImage;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewImageRepositoryImpl implements ReviewImageRepository {

    private final ReviewImageJpaRepository reviewImageJpaRepository;

    @Override
    public List<TopReviewImage> findTopReviewImagesByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit) {
        return reviewImageJpaRepository
                .findTopReviewImagesByStadiumIdAndBlockCode(
                        stadiumId, blockCode, PageRequest.of(0, limit))
                .stream()
                .map(
                        dto ->
                                TopReviewImage.builder()
                                        .url(dto.url())
                                        .reviewId(dto.reviewId())
                                        .blockCode(dto.blockCode())
                                        .rowNumber(dto.rowNumber())
                                        .seatNumber(dto.seatNumber())
                                        .build())
                .collect(Collectors.toList());
    }
}
