package org.depromeet.spot.application.stadium.dto.response;

import org.depromeet.spot.usecase.port.in.team.ReadStadiumHomeTeamUsecase.HomeTeamInfo;

public record HomeTeamInfoResponse(
        Long id, String alias, String backgroundColor, String fontColor) {

    public static HomeTeamInfoResponse from(HomeTeamInfo homeTeamInfo) {
        return new HomeTeamInfoResponse(
                homeTeamInfo.getId(),
                homeTeamInfo.getAlias(),
                homeTeamInfo.getLabelBackgroundColor(),
                homeTeamInfo.getLabelFontColor());
    }
}
