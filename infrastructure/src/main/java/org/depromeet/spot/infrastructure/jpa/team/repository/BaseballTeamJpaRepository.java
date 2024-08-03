package org.depromeet.spot.infrastructure.jpa.team.repository;

import java.util.List;

import org.depromeet.spot.infrastructure.jpa.team.entity.BaseballTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseballTeamJpaRepository extends JpaRepository<BaseballTeamEntity, Long> {
    boolean existsByNameIn(List<String> names);

    boolean existsById(Long teamId);
}
