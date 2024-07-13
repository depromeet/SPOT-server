package org.depromeet.spot.jpa.section.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.common.RgbCode;
import org.depromeet.spot.domain.section.Section;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "sections")
@NoArgsConstructor
public class SectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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

    public SectionEntity(
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

    public static SectionEntity from(Section section) {
        RgbCode labelRgbCode = section.getLabelRgbCode();
        return new SectionEntity(
                section.getId(),
                section.getStadiumId(),
                section.getName(),
                section.getAlias(),
                labelRgbCode.getRed(),
                labelRgbCode.getGreen(),
                labelRgbCode.getBlue());
    }

    public Section toDomain() {
        RgbCode rgbCode = RgbCode.builder().red(red).green(green).blue(blue).build();
        return new Section(id, stadiumId, name, alias, rgbCode);
    }
}
