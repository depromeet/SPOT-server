package org.depromeet.spot.jpa.review.repository.image;

import java.util.List;

import org.depromeet.spot.domain.review.ReviewImage;
import org.depromeet.spot.jpa.review.repository.ReviewImageCustomRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewImageRepositoryImpl implements ReviewImageRepository {
    private final ReviewImageJpaRepository reviewImageJpaRepository;
    private final ReviewImageCustomRepository reviewImageCustomRepository;

    @Override
    public List<ReviewImage> findTopReviewImagesByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit) {
        return reviewImageCustomRepository.findTopReviewImagesByStadiumIdAndBlockCode(
                stadiumId, blockCode, limit);
    }
}
