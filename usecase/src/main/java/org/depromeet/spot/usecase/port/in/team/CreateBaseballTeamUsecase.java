package org.depromeet.spot.usecase.port.in.team;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;

public interface CreateBaseballTeamUsecase {

    void save(CreateBaseballTeamCommand command);

    @Builder
    record CreateBaseballTeamCommand(
            MultipartFile logo, String name, String alias, String labelBackgroundColor) {}
}
