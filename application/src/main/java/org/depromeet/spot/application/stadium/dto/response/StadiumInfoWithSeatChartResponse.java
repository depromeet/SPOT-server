package org.depromeet.spot.application.stadium.dto.response;

import java.util.List;

import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase.StadiumInfoWithSeatChart;

import lombok.Builder;

@Builder
public record StadiumInfoWithSeatChartResponse(
        Long id,
        String name,
        String seatChartWithLabel,
        String thumbnail,
        List<HomeTeamInfoResponse> homeTeams,
        List<StadiumSectionInfoResponse> sections,
        List<StadiumBlockTagResponse> blockTags) {

    public static StadiumInfoWithSeatChartResponse from(
            StadiumInfoWithSeatChart stadiumInfoWithSeatChart) {
        List<HomeTeamInfoResponse> homeTeams =
                stadiumInfoWithSeatChart.getHomeTeams().stream()
                        .map(HomeTeamInfoResponse::from)
                        .toList();

        List<StadiumSectionInfoResponse> sections =
                stadiumInfoWithSeatChart.getSections().stream()
                        .map(StadiumSectionInfoResponse::from)
                        .toList();

        List<StadiumBlockTagResponse> blockTags =
                stadiumInfoWithSeatChart.getBlockTags().stream()
                        .map(StadiumBlockTagResponse::from)
                        .toList();

        return new StadiumInfoWithSeatChartResponse(
                stadiumInfoWithSeatChart.getId(),
                stadiumInfoWithSeatChart.getName(),
                stadiumInfoWithSeatChart.getSeatChartWithLabel(),
                stadiumInfoWithSeatChart.getThumbnail(),
                homeTeams,
                sections,
                blockTags);
    }
}
