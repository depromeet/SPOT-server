package org.depromeet.spot.infrastructure.jpa.member.repository;

import java.util.Optional;

import org.depromeet.spot.infrastructure.jpa.member.entity.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelJpaRepository extends JpaRepository<LevelEntity, Long> {

    Optional<LevelEntity> findByValue(int value);
}
