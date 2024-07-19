package org.depromeet.spot.usecase.service.team;

import java.util.List;

import org.depromeet.spot.common.exception.team.TeamException.DuplicateTeamNameException;
import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.team.CreateBaseballTeamUsecase;
import org.depromeet.spot.usecase.port.out.media.ImageUploadPort;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class CreateBaseballTeamService implements CreateBaseballTeamUsecase {

    private final BaseballTeamRepository baseballTeamRepository;
    private final ImageUploadPort imageUploadPort;

    @Override
    public void save(CreateBaseballTeamCommand command) {
        final String name = command.name();
        String logoUrl = imageUploadPort.upload(name, command.logo(), MediaProperty.TEAM_LOGO);
        if (baseballTeamRepository.existsByNameIn(List.of(name))) {
            throw new DuplicateTeamNameException();
        }
        BaseballTeam team =
                BaseballTeam.builder()
                        .name(name)
                        .alias(command.alias())
                        .logo(logoUrl)
                        .labelRgbCode(command.rgbCode())
                        .build();
        baseballTeamRepository.saveAll(List.of(team));
    }
}
