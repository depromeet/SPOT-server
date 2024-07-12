package org.depromeet.spot.domain.team;

import lombok.Getter;

@Getter
public class StadiumHomeTeam {

    private final Long id;
    private final Long stadiumId;
    private final Long teamId;

    public StadiumHomeTeam(Long id, Long stadiumId, Long teamId) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.teamId = teamId;
    }
}
