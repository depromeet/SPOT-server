package org.depromeet.spot.infrastructure.jpa.team.repository.hometeam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.domain.team.StadiumHomeTeam;
import org.depromeet.spot.infrastructure.jpa.stadium.entity.StadiumEntity;
import org.depromeet.spot.infrastructure.jpa.team.entity.BaseballTeamEntity;
import org.depromeet.spot.infrastructure.jpa.team.entity.StadiumHomeTeamEntity;
import org.depromeet.spot.usecase.port.out.team.HomeTeamRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HomeTeamRepositoryImpl implements HomeTeamRepository {

    private final StadiumHomeTeamCustomRepository stadiumHomeTeamCustomRepository;
    private final HomeTeamJpaRepository homeTeamJpaRepository;

    @Override
    public List<BaseballTeam> findAllHomeTeamByStadium(final Long stadiumId) {
        List<BaseballTeamEntity> homeTeamEntities =
                stadiumHomeTeamCustomRepository.findAllHomeTeamByStadium(stadiumId);
        return homeTeamEntities.stream().map(BaseballTeamEntity::toDomain).toList();
    }

    @Override
    public Map<Stadium, List<BaseballTeam>> findAllStadiumHomeTeam() {
        Map<StadiumEntity, List<BaseballTeamEntity>> stadiumHomeTeamMap =
                stadiumHomeTeamCustomRepository.findAllStadiumHomeTeam();
        return stadiumHomeTeamMap.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey().toDomain(),
                                entry ->
                                        entry.getValue().stream()
                                                .map(BaseballTeamEntity::toDomain)
                                                .toList()));
    }

    @Override
    public void saveAll(List<StadiumHomeTeam> homeTeams) {
        List<StadiumHomeTeamEntity> entities =
                homeTeams.stream().map(StadiumHomeTeamEntity::from).toList();
        homeTeamJpaRepository.saveAll(entities);
    }
}
