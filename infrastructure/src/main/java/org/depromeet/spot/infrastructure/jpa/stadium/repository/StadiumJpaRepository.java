package org.depromeet.spot.infrastructure.jpa.stadium.repository;

import org.depromeet.spot.infrastructure.jpa.stadium.entity.StadiumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StadiumJpaRepository extends JpaRepository<StadiumEntity, Long> {}
