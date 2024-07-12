package org.depromeet.spot.jpa.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.team.BaseballTeam;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "baseball_teams")
@NoArgsConstructor
public class BaseballTeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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

    public BaseballTeamEntity(
            Long id,
            String name,
            String alias,
            String logo,
            Integer red,
            Integer green,
            Integer blue) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.logo = logo;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static BaseballTeamEntity from(BaseballTeam baseballTeam) {
        return new BaseballTeamEntity(
                baseballTeam.getId(),
                baseballTeam.getName(),
                baseballTeam.getAlias(),
                baseballTeam.getLogo(),
                baseballTeam.getRed(),
                baseballTeam.getGreen(),
                baseballTeam.getBlue());
    }

    public BaseballTeam toDomain() {
        return new BaseballTeam(id, name, alias, logo, red, green, blue);
    }
}
