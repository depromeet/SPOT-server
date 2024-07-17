package org.depromeet.spot.usecase.port.out.team;

import java.util.List;

import org.depromeet.spot.domain.team.BaseballTeam;

public interface BaseballTeamRepository {
    BaseballTeam findById(Long id);

    List<BaseballTeam> findAll();

    void saveAll(List<BaseballTeam> teams);

    boolean existsByNameIn(List<String> names);

    boolean existsById(Long id);
}
