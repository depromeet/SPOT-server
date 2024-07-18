package org.depromeet.spot.jpa.review.repository.image;

import java.util.List;

import org.depromeet.spot.domain.review.ReviewImage;
import org.depromeet.spot.jpa.review.entity.ReviewImageEntity;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewImageRepositoryImpl implements ReviewImageRepository {

    private final ReviewImageJpaRepository reviewImageJpaRepository;

    @Override
    public List<ReviewImage> saveAll(List<ReviewImage> images) {
        List<ReviewImageEntity> entities =
                reviewImageJpaRepository.saveAll(
                        images.stream().map(ReviewImageEntity::from).toList());
        return entities.stream().map(ReviewImageEntity::toDomain).toList();
    }
}
