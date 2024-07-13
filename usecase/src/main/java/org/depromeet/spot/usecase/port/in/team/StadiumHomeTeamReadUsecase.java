package org.depromeet.spot.usecase.port.in.team;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.common.RgbCode;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.domain.team.BaseballTeam;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface StadiumHomeTeamReadUsecase {

    List<HomeTeamInfo> findByStadium(Long stadiumId);

    Map<Stadium, List<BaseballTeam>> findAllStadiumHomeTeam();

    @Getter
    @AllArgsConstructor
    class HomeTeamInfo {
        private final Long id;
        private final String alias;
        private final RgbCode rgbCode;
    }
}
