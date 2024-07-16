package org.depromeet.spot.usecase.port.in.team;

import java.util.List;

import org.depromeet.spot.domain.team.BaseballTeam;

public interface CreateBaseballTeamUsecase {

    void saveAll(List<BaseballTeam> teams);
}
