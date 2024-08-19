package org.depromeet.spot.infrastructure.jpa.hashtag.repository;

import org.depromeet.spot.infrastructure.jpa.hashtag.entity.HashTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagJpaRepository extends JpaRepository<HashTagEntity, Long> {}
