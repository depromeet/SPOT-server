package org.depromeet.spot.jpa.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "baseball_teams")
@NoArgsConstructor
@AllArgsConstructor
public class BaseballTeamEntity extends BaseEntity {

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "alias", nullable = false, length = 10)
    private String alias;

    @Column(name = "logo", nullable = false, length = 255)
    private String logo;

    @Column(name = "label_background_color", nullable = false, unique = true, length = 10)
    private String labelBackgroundColor;

    public static BaseballTeamEntity from(BaseballTeam baseballTeam) {
        return new BaseballTeamEntity(
                baseballTeam.getName(),
                baseballTeam.getAlias(),
                baseballTeam.getLogo(),
                baseballTeam.getLabelBackgroundColor());
    }

    public BaseballTeam toDomain() {
        return new BaseballTeam(this.getId(), name, alias, logo, labelBackgroundColor);
    }
}
