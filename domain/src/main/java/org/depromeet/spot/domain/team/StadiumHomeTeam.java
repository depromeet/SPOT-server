package org.depromeet.spot.domain.team;

import org.depromeet.spot.domain.stadium.Stadium;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StadiumHomeTeam {

    private final Long id;
    private final Stadium stadium;
    private final BaseballTeam baseballTeam;
}
