package org.depromeet.spot.usecase.port.out.team;

import java.util.List;

import org.depromeet.spot.domain.team.BaseballTeam;

public interface BaseballTeamRepository {

    List<BaseballTeam> findAllHomeTeamByStadium(Long stadiumId);
}
