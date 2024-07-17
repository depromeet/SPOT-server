package org.depromeet.spot.jpa.team.repository;

import java.util.List;

import org.depromeet.spot.common.exception.team.TeamException.BaseballTeamNotFoundException;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.jpa.team.entity.BaseballTeamEntity;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BaseballTeamRepositoryImpl implements BaseballTeamRepository {

    private final BaseballTeamJpaRepository baseballTeamJpaRepository;
    private final BaseballTeamJdbcRepository baseballTeamJdbcRepository;

    @Override
    public BaseballTeam findById(final Long id) {
        BaseballTeamEntity entity =
                baseballTeamJpaRepository
                        .findById(id)
                        .orElseThrow(BaseballTeamNotFoundException::new);
        return entity.toDomain();
    }

    @Override
    public List<BaseballTeam> findAll() {
        List<BaseballTeamEntity> entities = baseballTeamJpaRepository.findAll();
        return entities.stream().map(BaseballTeamEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<BaseballTeam> teams) {
        baseballTeamJdbcRepository.createBaseballTeams(teams);
    }

    @Override
    public boolean existsByNameIn(List<String> names) {
        return baseballTeamJpaRepository.existsByNameIn(names);
    }

    @Override
    public boolean existsById(Long id) {
        return baseballTeamJpaRepository.existsById(id);
    }
}
