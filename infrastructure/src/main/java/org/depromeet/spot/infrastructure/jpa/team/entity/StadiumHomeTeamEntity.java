package org.depromeet.spot.infrastructure.jpa.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.team.StadiumHomeTeam;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stadium_home_teams")
@NoArgsConstructor
@AllArgsConstructor
public class StadiumHomeTeamEntity extends BaseEntity {

    @Column(name = "stadium_id", nullable = false)
    private Long stadiumId;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    public static StadiumHomeTeamEntity from(StadiumHomeTeam stadiumHomeTeam) {
        return new StadiumHomeTeamEntity(
                stadiumHomeTeam.getStadiumId(), stadiumHomeTeam.getTeamId());
    }

    public StadiumHomeTeam toDomain() {
        return new StadiumHomeTeam(this.getId(), stadiumId, teamId);
    }
}
