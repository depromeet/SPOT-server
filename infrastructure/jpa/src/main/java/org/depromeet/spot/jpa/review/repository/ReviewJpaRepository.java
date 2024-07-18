package org.depromeet.spot.jpa.review.repository;

import org.depromeet.spot.jpa.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {}
