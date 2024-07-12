package org.depromeet.spot.usecase.port.in.team;

import java.util.List;

import org.depromeet.spot.domain.common.RgbCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface StadiumHomeTeamReadUsecase {

    List<HomeTeamInfo> findByStadium(Long stadiumId);

    @Getter
    @AllArgsConstructor
    class HomeTeamInfo {
        private final Long id;
        private final String alias;
        private final RgbCode rgbCode;
    }
}
