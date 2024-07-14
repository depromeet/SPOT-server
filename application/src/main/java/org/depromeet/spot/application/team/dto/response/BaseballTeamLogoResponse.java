package org.depromeet.spot.application.team.dto.response;

import org.depromeet.spot.domain.team.BaseballTeam;

public record BaseballTeamLogoResponse(Long id, String name, String logo) {

    public static BaseballTeamLogoResponse from(BaseballTeam team) {
        return new BaseballTeamLogoResponse(team.getId(), team.getName(), team.getLogo());
    }
}
