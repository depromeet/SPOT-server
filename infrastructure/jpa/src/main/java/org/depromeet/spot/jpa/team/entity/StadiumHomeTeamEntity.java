package org.depromeet.spot.jpa.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.team.StadiumHomeTeam;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "stadium_home_teams")
@NoArgsConstructor
public class StadiumHomeTeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "stadium_id", nullable = false)
    private Long stadiumId;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    public StadiumHomeTeamEntity(Long id, Long stadiumId, Long teamId) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.teamId = teamId;
    }

    public static StadiumHomeTeamEntity from(StadiumHomeTeam stadiumHomeTeam) {
        return new StadiumHomeTeamEntity(
                stadiumHomeTeam.getId(),
                stadiumHomeTeam.getStadiumId(),
                stadiumHomeTeam.getTeamId());
    }

    public StadiumHomeTeam toDomain() {
        return new StadiumHomeTeam(id, stadiumId, teamId);
    }
}
