package org.depromeet.spot.jpa.stadium.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.stadium.Stadium;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "stadiums")
@NoArgsConstructor
public class StadiumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "main_image", length = 255)
    private String mainImage;

    @Column(name = "seating_chart_image", length = 255)
    private String seatingChartImage;

    @Column(name = "labeled_seating_chart_image", length = 255)
    private String labeledSeatingChartImage;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public StadiumEntity(
            Long id,
            String name,
            String mainImage,
            String seatingChartImage,
            String labeledSeatingChartImage,
            boolean isActive) {
        this.id = id;
        this.name = name;
        this.mainImage = mainImage;
        this.seatingChartImage = seatingChartImage;
        this.labeledSeatingChartImage = labeledSeatingChartImage;
        this.isActive = isActive;
    }

    public static StadiumEntity from(Stadium stadium) {
        return new StadiumEntity(
                stadium.getId(),
                stadium.getName(),
                stadium.getMainImage(),
                stadium.getSeatingChartImage(),
                stadium.getLabeledSeatingChartImage(),
                stadium.isActive());
    }

    public Stadium toDomain() {
        return Stadium.builder()
                .id(id)
                .name(name)
                .mainImage(mainImage)
                .seatingChartImage(seatingChartImage)
                .labeledSeatingChartImage(labeledSeatingChartImage)
                .isActive(isActive)
                .build();
    }
}
