package org.depromeet.spot.usecase.port.in.team;

import org.depromeet.spot.domain.common.RgbCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface StadiumHomeTeamReadUsecase {

    @Getter
    @AllArgsConstructor
    class HomeTeamInfo {
        private final Long id;
        private final String alias;
        private final RgbCode rgbCode;
    }
}
