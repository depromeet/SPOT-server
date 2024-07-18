package org.depromeet.spot.usecase.service.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.depromeet.spot.domain.team.StadiumHomeTeam;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.in.team.CreateHomeTeamUsecase;
import org.depromeet.spot.usecase.port.in.team.ReadBaseballTeamUsecase;
import org.depromeet.spot.usecase.port.out.team.HomeTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateHomeTeamService implements CreateHomeTeamUsecase {

    private final HomeTeamRepository homeTeamRepository;
    private final StadiumReadUsecase stadiumReadUsecase;
    private final ReadBaseballTeamUsecase readBaseballTeamUsecase;

    @Override
    public void createHomeTeams(final Long stadiumId, Set<Long> teamIds) {
        stadiumReadUsecase.checkIsExistsBy(stadiumId);
        readBaseballTeamUsecase.areAllTeamIdsExist(teamIds);
        List<StadiumHomeTeam> homeTeams = new ArrayList<>();
        teamIds.forEach(
                teamId ->
                        homeTeams.add(
                                StadiumHomeTeam.builder()
                                        .stadiumId(stadiumId)
                                        .teamId(teamId)
                                        .build()));
        homeTeamRepository.saveAll(homeTeams);
    }
}
