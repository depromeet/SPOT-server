package org.depromeet.spot.infrastructure.jpa.stadium.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stadiums")
@NoArgsConstructor
@AllArgsConstructor
public class StadiumEntity extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "main_image", length = 300)
    private String mainImage;

    @Column(name = "seating_chart_image", length = 300)
    private String seatingChartImage;

    @Column(name = "labeled_seating_chart_image", length = 300)
    private String labeledSeatingChartImage;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public static StadiumEntity from(Stadium stadium) {
        return new StadiumEntity(
                stadium.getName(),
                stadium.getMainImage(),
                stadium.getSeatingChartImage(),
                stadium.getLabeledSeatingChartImage(),
                stadium.isActive());
    }

    public static StadiumEntity withStadium(Stadium stadium) {
        return new StadiumEntity(stadium);
    }

    public StadiumEntity(Stadium stadium) {
        super(stadium.getId(), null, null, null);
        name = stadium.getName();
        mainImage = stadium.getMainImage();
        seatingChartImage = stadium.getSeatingChartImage();
        labeledSeatingChartImage = stadium.getLabeledSeatingChartImage();
        isActive = stadium.isActive();
    }

    public Stadium toDomain() {
        return Stadium.builder()
                .id(this.getId())
                .name(name)
                .mainImage(mainImage)
                .seatingChartImage(seatingChartImage)
                .labeledSeatingChartImage(labeledSeatingChartImage)
                .isActive(isActive)
                .build();
    }
}
