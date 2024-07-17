package org.depromeet.spot.usecase.port.in.team;

import java.util.List;
import java.util.Set;

import org.depromeet.spot.domain.team.BaseballTeam;

public interface ReadBaseballTeamUsecase {

    BaseballTeam findById(Long id);

    List<BaseballTeam> findAll();

    void checkExistsBy(Set<Long> teamIds);
}
