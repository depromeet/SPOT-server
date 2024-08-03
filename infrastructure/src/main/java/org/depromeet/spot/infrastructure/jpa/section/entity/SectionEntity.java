package org.depromeet.spot.infrastructure.jpa.section.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.common.HexCode;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

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

    @Column(name = "label_color", nullable = false)
    private String labelColor;

    public static SectionEntity from(Section section) {
        return new SectionEntity(
                section.getStadiumId(),
                section.getName(),
                section.getAlias(),
                section.getLabelColor().getValue());
    }

    public static SectionEntity withSection(Section section) {
        return new SectionEntity(section);
    }

    public SectionEntity(Section section) {
        super(section.getId(), null, null, null);
        stadiumId = section.getStadiumId();
        name = section.getName();
        alias = section.getAlias();
        labelColor = section.getLabelColor().getValue();
    }

    public Section toDomain() {
        HexCode labelHexColor = new HexCode(labelColor);
        return new Section(this.getId(), stadiumId, name, alias, labelHexColor);
    }
}