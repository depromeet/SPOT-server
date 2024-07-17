package org.depromeet.spot.usecase.port.in.stadium;

import java.util.List;

import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.team.ReadStadiumHomeTeamUsecase.HomeTeamInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface StadiumReadUsecase {

    List<StadiumHomeTeamInfo> findAllStadiums();

    List<StadiumNameInfo> findAllNames();

    StadiumInfoWithSeatChart findWithSeatChartById(Long id);

    Stadium findById(Long id);

    boolean existsById(Long id);

    void checkIsExistsBy(Long stadiumId);

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
    @Builder
    @AllArgsConstructor
    class StadiumInfoWithSeatChart {
        private final Long id;
        private final String name;
        private final List<HomeTeamInfo> homeTeams;
        private final String seatChartWithLabel;
        private final String thumbnail;
    }

    @Getter
    @AllArgsConstructor
    class StadiumNameInfo {
        private final Long id;
        private final String name;
    }
}
