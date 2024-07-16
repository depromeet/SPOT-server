package org.depromeet.spot.application.stadium.dto.response;

import org.depromeet.spot.application.common.dto.RgbCodeResponse;
import org.depromeet.spot.domain.common.RgbCode;
import org.depromeet.spot.usecase.port.in.team.ReadStadiumHomeTeamUsecase.HomeTeamInfo;

public record HomeTeamInfoResponse(Long id, String alias, RgbCodeResponse color) {

    public static HomeTeamInfoResponse from(HomeTeamInfo homeTeamInfo) {
        RgbCode rgbCode = homeTeamInfo.getRgbCode();
        RgbCodeResponse rgbCodeRes =
                RgbCodeResponse.builder()
                        .red(rgbCode.getRed())
                        .blue(rgbCode.getBlue())
                        .green(rgbCode.getGreen())
                        .build();

        return new HomeTeamInfoResponse(homeTeamInfo.getId(), homeTeamInfo.getAlias(), rgbCodeRes);
    }
}
