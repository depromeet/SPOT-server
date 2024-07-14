package org.depromeet.spot.jpa.team.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.jpa.stadium.entity.StadiumEntity;
import org.depromeet.spot.jpa.team.entity.BaseballTeamEntity;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BaseballTeamRepositoryImpl implements BaseballTeamRepository {

    private final StadiumHomeTeamCustomRepository stadiumHomeTeamCustomRepository;
    private final BaseballTeamJpaRepository baseballTeamJpaRepository;

    @Override
    public List<BaseballTeam> findAll() {
        List<BaseballTeamEntity> entities = baseballTeamJpaRepository.findAll();
        return entities.stream().map(BaseballTeamEntity::toDomain).toList();
    }

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
}
