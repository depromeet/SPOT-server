package org.depromeet.spot.domain.section;

import lombok.Getter;

@Getter
public class Section {

    public final Long id;
    public final Long stadiumId;
    public final String name;
    public final String alias;
    public final Integer red;
    public final Integer green;
    public final Integer blue;

    public Section(
            Long id,
            Long stadiumId,
            String name,
            String alias,
            Integer red,
            Integer green,
            Integer blue) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.name = name;
        this.alias = alias;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
}
