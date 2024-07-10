package org.depromeet.spot.domain.team;

import lombok.Getter;

@Getter
public class BaseballTeam {

    public final Long id;
    public final String name;
    public final String alias;
    public final String logo;
    public final Integer red;
    public final Integer green;
    public final Integer blue;

    public BaseballTeam(
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
}
