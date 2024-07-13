package org.depromeet.spot.application.stadium.dto.response;

import java.util.List;

import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase.StadiumHomeTeamInfo;

import lombok.Builder;

@Builder
public record StadiumHomeTeamInfoResponse(
        Long id,
        String name,
        String thumbnail,
        List<HomeTeamInfoResponse> homeTeams,
        boolean isActive) {
    public static StadiumHomeTeamInfoResponse from(StadiumHomeTeamInfo stadiumHomeTeamInfo) {
        List<HomeTeamInfoResponse> homeTeams =
                stadiumHomeTeamInfo.getHomeTeams().stream()
                        .map(HomeTeamInfoResponse::from)
                        .toList();

        return new StadiumHomeTeamInfoResponse(
                stadiumHomeTeamInfo.getId(),
                stadiumHomeTeamInfo.getName(),
                stadiumHomeTeamInfo.getThumbnail(),
                homeTeams,
                stadiumHomeTeamInfo.isActive());
    }
}
