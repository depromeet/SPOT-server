package org.depromeet.spot.domain.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StadiumHomeTeam {

    private final Long id;
    private final Long stadiumId;
    private final Long teamId;
}
