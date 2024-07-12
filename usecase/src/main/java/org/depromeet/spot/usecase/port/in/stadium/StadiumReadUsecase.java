package org.depromeet.spot.usecase.port.in.stadium;

import java.util.List;

import org.depromeet.spot.usecase.port.in.team.StadiumHomeTeamReadUsecase.HomeTeamInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface StadiumReadUsecase {

    List<StadiumHomeTeamInfo> findAllStadiums();

    List<StadiumNameInfo> findAllNames();

    StadiumInfoWithSeatChart findWithSeatChartById(Long id);

    @Getter
    @AllArgsConstructor
    class StadiumHomeTeamInfo {
        private final Long id;
        private final String name;
        private final List<HomeTeamInfo> homeTeams;
        private final String thumbnail;
        private final boolean isActive;
    }

    @Getter
    @AllArgsConstructor
    class StadiumInfoWithSeatChart {
        private final Long id;
        private final String name;
        private final List<HomeTeamInfo> homeTeams;
        private final String seatChartWithLabel;
    }

    @Getter
    @AllArgsConstructor
    class StadiumNameInfo {
        private final Long id;
        private final String name;
    }
}
