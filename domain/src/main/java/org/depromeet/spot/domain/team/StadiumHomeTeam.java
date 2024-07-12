package org.depromeet.spot.domain.team;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StadiumHomeTeam {

    private final Long id;
    private final Long stadiumId;
    private final Long teamId;
}
