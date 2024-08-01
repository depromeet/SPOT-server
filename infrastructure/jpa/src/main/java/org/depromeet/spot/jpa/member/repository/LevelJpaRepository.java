package org.depromeet.spot.jpa.member.repository;

import org.depromeet.spot.jpa.member.entity.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelJpaRepository extends JpaRepository<LevelEntity, Long> {

    LevelEntity findByValue(int value);
}
