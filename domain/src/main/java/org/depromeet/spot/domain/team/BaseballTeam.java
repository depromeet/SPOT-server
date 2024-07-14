package org.depromeet.spot.domain.team;

import org.depromeet.spot.domain.common.RgbCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseballTeam {

    private final Long id;
    private final String name;
    private final String alias;
    private final String logo;
    private final RgbCode labelRgbCode;

    public BaseballTeam(Long id, String name, String alias, String logo, RgbCode labelRgbCode) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.logo = logo;
        this.labelRgbCode = labelRgbCode;
    }
}
