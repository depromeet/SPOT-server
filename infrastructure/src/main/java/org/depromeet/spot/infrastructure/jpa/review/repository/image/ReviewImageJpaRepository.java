package org.depromeet.spot.infrastructure.jpa.review.repository.image;

import org.depromeet.spot.infrastructure.jpa.review.entity.image.ReviewImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewImageJpaRepository extends JpaRepository<ReviewImageEntity, Long> {}
