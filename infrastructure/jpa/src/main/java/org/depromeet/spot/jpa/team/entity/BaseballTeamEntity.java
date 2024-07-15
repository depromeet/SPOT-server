package org.depromeet.spot.jpa.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.common.RgbCode;
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

    @Column(name = "red")
    private Integer red;

    @Column(name = "green")
    private Integer green;

    @Column(name = "blue")
    private Integer blue;

    public static BaseballTeamEntity from(BaseballTeam baseballTeam) {
        RgbCode labelRgbCode = baseballTeam.getLabelRgbCode();
        return new BaseballTeamEntity(
                baseballTeam.getName(),
                baseballTeam.getAlias(),
                baseballTeam.getLogo(),
                labelRgbCode.getRed(),
                labelRgbCode.getGreen(),
                labelRgbCode.getBlue());
    }

    public BaseballTeam toDomain() {
        RgbCode labelRgbCode = RgbCode.builder().red(red).green(green).blue(blue).build();
        return new BaseballTeam(this.getId(), name, alias, logo, labelRgbCode);
    }
}
