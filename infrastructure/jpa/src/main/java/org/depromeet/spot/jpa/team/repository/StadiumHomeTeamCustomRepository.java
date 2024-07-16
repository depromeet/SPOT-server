package org.depromeet.spot.jpa.team.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static org.depromeet.spot.jpa.stadium.entity.QStadiumEntity.stadiumEntity;
import static org.depromeet.spot.jpa.team.entity.QBaseballTeamEntity.baseballTeamEntity;
import static org.depromeet.spot.jpa.team.entity.QStadiumHomeTeamEntity.stadiumHomeTeamEntity;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.jpa.stadium.entity.StadiumEntity;
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

    public Map<StadiumEntity, List<BaseballTeamEntity>> findAllStadiumHomeTeam() {
        return queryFactory
                .from(stadiumHomeTeamEntity)
                .join(stadiumEntity)
                .on(stadiumHomeTeamEntity.stadiumId.eq(stadiumEntity.id))
                .join(baseballTeamEntity)
                .on(stadiumHomeTeamEntity.teamId.eq(baseballTeamEntity.id))
                .transform(groupBy(stadiumEntity).as(list(baseballTeamEntity)));
    }
}
