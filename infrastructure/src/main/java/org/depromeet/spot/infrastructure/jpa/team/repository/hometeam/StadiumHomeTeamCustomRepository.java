package org.depromeet.spot.infrastructure.jpa.team.repository.hometeam;

import static com.querydsl.core.group.GroupBy.list;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.infrastructure.jpa.stadium.entity.QStadiumEntity;
import org.depromeet.spot.infrastructure.jpa.stadium.entity.StadiumEntity;
import org.depromeet.spot.infrastructure.jpa.team.entity.BaseballTeamEntity;
import org.depromeet.spot.infrastructure.jpa.team.entity.QBaseballTeamEntity;
import org.depromeet.spot.infrastructure.jpa.team.entity.QStadiumHomeTeamEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StadiumHomeTeamCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<BaseballTeamEntity> findAllHomeTeamByStadium(final Long stadiumId) {
        return queryFactory
                .select(QBaseballTeamEntity.baseballTeamEntity)
                .from(QStadiumHomeTeamEntity.stadiumHomeTeamEntity)
                .join(QBaseballTeamEntity.baseballTeamEntity)
                .on(
                        QStadiumHomeTeamEntity.stadiumHomeTeamEntity.teamId.eq(
                                QBaseballTeamEntity.baseballTeamEntity.id))
                .where(QStadiumHomeTeamEntity.stadiumHomeTeamEntity.stadiumId.eq(stadiumId))
                .fetch();
    }

    public Map<StadiumEntity, List<BaseballTeamEntity>> findAllStadiumHomeTeam() {
        return queryFactory
                .from(QStadiumHomeTeamEntity.stadiumHomeTeamEntity)
                .join(QStadiumEntity.stadiumEntity)
                .on(
                        QStadiumHomeTeamEntity.stadiumHomeTeamEntity.stadiumId.eq(
                                QStadiumEntity.stadiumEntity.id))
                .join(QBaseballTeamEntity.baseballTeamEntity)
                .on(
                        QStadiumHomeTeamEntity.stadiumHomeTeamEntity.teamId.eq(
                                QBaseballTeamEntity.baseballTeamEntity.id))
                .orderBy(QStadiumEntity.stadiumEntity.name.asc())
                .transform(
                        GroupBy.groupBy(QStadiumEntity.stadiumEntity)
                                .as(list(QBaseballTeamEntity.baseballTeamEntity)));
    }
}
