package org.depromeet.spot.usecase.service.team;

import java.util.List;

import org.depromeet.spot.common.exception.team.TeamException.DuplicateTeamNameException;
import org.depromeet.spot.domain.common.HexCode;
import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.team.CreateBaseballTeamUsecase;
import org.depromeet.spot.usecase.port.out.media.ImageUploadPort;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@Transactional
@RequiredArgsConstructor
public class CreateBaseballTeamService implements CreateBaseballTeamUsecase {

    private final BaseballTeamRepository baseballTeamRepository;
    private final ImageUploadPort imageUploadPort;

    @Override
    public void save(CreateBaseballTeamCommand command) {
        final String name = command.name();
        checkExistsName(name);
        final String logoUrl =
                imageUploadPort.upload(name, command.logo(), MediaProperty.TEAM_LOGO);
        HexCode backgroundColor = new HexCode(command.labelBackgroundColor());
        BaseballTeam team =
                BaseballTeam.builder()
                        .name(name)
                        .alias(command.alias())
                        .logo(logoUrl)
                        .labelBackgroundColor(backgroundColor)
                        .build();
        baseballTeamRepository.saveAll(List.of(team));
    }

    public void checkExistsName(final String name) {
        if (baseballTeamRepository.existsByNameIn(List.of(name))) {
            throw new DuplicateTeamNameException();
        }
    }
}
