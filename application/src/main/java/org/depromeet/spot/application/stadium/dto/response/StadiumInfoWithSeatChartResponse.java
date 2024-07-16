package org.depromeet.spot.application.stadium.dto.response;

import java.util.List;

import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase.StadiumInfoWithSeatChart;

import lombok.Builder;

@Builder
public record StadiumInfoWithSeatChartResponse(
        Long id, String name, String seatChartWithLabel, List<HomeTeamInfoResponse> homeTeams) {

    public static StadiumInfoWithSeatChartResponse from(
            StadiumInfoWithSeatChart stadiumInfoWithSeatChart) {
        List<HomeTeamInfoResponse> homeTeams =
                stadiumInfoWithSeatChart.getHomeTeams().stream()
                        .map(HomeTeamInfoResponse::from)
                        .toList();

        return new StadiumInfoWithSeatChartResponse(
                stadiumInfoWithSeatChart.getId(),
                stadiumInfoWithSeatChart.getName(),
                stadiumInfoWithSeatChart.getSeatChartWithLabel(),
                homeTeams);
    }
}
