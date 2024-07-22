package org.depromeet.spot.domain.section;

import org.depromeet.spot.domain.common.RgbCode;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class Section {

    private final Long id;
    private final Long stadiumId;
    private final String name;
    private final String alias;
    private final RgbCode labelRgbCode;

    public Section(Long id, Long stadiumId, String name, String alias, RgbCode labelRgbCode) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.name = name;
        this.alias = alias;
        this.labelRgbCode = labelRgbCode;
    }
}
