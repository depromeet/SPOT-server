package org.depromeet.spot.infrastructure.jpa.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.common.HexCode;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

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

    @Column(name = "label_font_color", nullable = false, length = 10)
    private String labelFontColor;

    public static BaseballTeamEntity from(BaseballTeam baseballTeam) {
        return new BaseballTeamEntity(
                baseballTeam.getName(),
                baseballTeam.getAlias(),
                baseballTeam.getLogo(),
                baseballTeam.getLabelBackgroundColor().getValue(),
                baseballTeam.getLabelFontColor().getValue());
    }

    public BaseballTeam toDomain() {
        HexCode backgroundColor = new HexCode(labelBackgroundColor);
        HexCode fontColor = new HexCode(labelFontColor);
        return new BaseballTeam(this.getId(), name, alias, logo, backgroundColor, fontColor);
    }
}
