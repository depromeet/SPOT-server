package org.depromeet.spot.application.team.dto.response;

import org.depromeet.spot.domain.team.BaseballTeam;

public record BaseballTeamLogoRes(Long id, String name, String logo) {

    public static BaseballTeamLogoRes from(BaseballTeam team) {
        return new BaseballTeamLogoRes(team.getId(), team.getName(), team.getLogo());
    }
}
