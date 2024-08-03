package org.depromeet.spot.infrastructure.jpa.team.repository.hometeam;

import org.depromeet.spot.infrastructure.jpa.team.entity.StadiumHomeTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeTeamJpaRepository extends JpaRepository<StadiumHomeTeamEntity, Long> {}
