package org.depromeet.spot.usecase.port.in.team;

import java.util.List;

public interface CreateHomeTeamUsecase {

    void createHomeTeam(Long stadiumId, List<Long> teamIds);
}
