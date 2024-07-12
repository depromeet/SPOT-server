package org.depromeet.spot.jpa.team.repository;

import static org.depromeet.spot.jpa.team.entity.QBaseballTeamEntity.baseballTeamEntity;
import static org.depromeet.spot.jpa.team.entity.QStadiumHomeTeamEntity.stadiumHomeTeamEntity;

import java.util.List;

import org.depromeet.spot.jpa.team.entity.BaseballTeamEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StadiumHomeTeamCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<BaseballTeamEntity> findAllHomeTeamByStadium(final Long stadiumId) {
        return queryFactory
                .select(baseballTeamEntity)
                .from(stadiumHomeTeamEntity)
                .join(baseballTeamEntity)
                .on(stadiumHomeTeamEntity.teamId.eq(baseballTeamEntity.id))
                .where(stadiumHomeTeamEntity.stadiumId.eq(stadiumId))
                .fetch();
    }
}
