package org.depromeet.spot.jpa.section.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.common.RgbCode;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sections")
@NoArgsConstructor
@AllArgsConstructor
public class SectionEntity extends BaseEntity {

    @Column(name = "stadium_id", nullable = false)
    private Long stadiumId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "alias", length = 20)
    private String alias;

    @Column(name = "red")
    private Integer red;

    @Column(name = "green")
    private Integer green;

    @Column(name = "blue")
    private Integer blue;

    public static SectionEntity from(Section section) {
        RgbCode labelRgbCode = section.getLabelRgbCode();
        return new SectionEntity(
                section.getStadiumId(),
                section.getName(),
                section.getAlias(),
                labelRgbCode.getRed(),
                labelRgbCode.getGreen(),
                labelRgbCode.getBlue());
    }

    public Section toDomain() {
        RgbCode rgbCode = RgbCode.builder().red(red).green(green).blue(blue).build();
        return new Section(this.getId(), stadiumId, name, alias, rgbCode);
    }
}
