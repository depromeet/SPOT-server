package org.depromeet.spot.jpa.team.repository;

import java.util.List;
import java.util.Set;

import org.depromeet.spot.jpa.team.entity.BaseballTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseballTeamJpaRepository extends JpaRepository<BaseballTeamEntity, Long> {
    boolean existsByNameIn(List<String> names);

    boolean existsByIdIn(Set<Long> ids);
}
