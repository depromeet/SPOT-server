package org.depromeet.spot.usecase.service.team;

import java.util.List;

import org.depromeet.spot.usecase.port.in.team.CreateHomeTeamUsecase;
import org.depromeet.spot.usecase.port.out.team.HomeTeamRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateHomeTeamService implements CreateHomeTeamUsecase {

    private final HomeTeamRepository homeTeamRepository;

    @Override
    public void createHomeTeam(Long stadiumId, List<Long> teamIds) {}
}
