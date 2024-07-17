package org.depromeet.spot.usecase.port.in.team;

import java.util.Set;

public interface CreateHomeTeamUsecase {

    void createHomeTeams(Long stadiumId, Set<Long> teamIds);
}
