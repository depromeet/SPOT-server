package org.depromeet.spot.usecase.port.out.team;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.domain.team.BaseballTeam;

public interface BaseballTeamRepository {
    List<BaseballTeam> findAll();

    List<BaseballTeam> findAllHomeTeamByStadium(Long stadiumId);

    Map<Stadium, List<BaseballTeam>> findAllStadiumHomeTeam();
}
