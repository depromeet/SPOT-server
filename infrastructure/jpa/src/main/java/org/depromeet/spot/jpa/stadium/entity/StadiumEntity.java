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

    public StadiumEntity(
            Long id,
            String name,
            String mainImage,
            String seatingChartImage,
            String labeledSeatingChartImage) {
        this.id = id;
        this.name = name;
        this.mainImage = mainImage;
        this.seatingChartImage = seatingChartImage;
        this.labeledSeatingChartImage = labeledSeatingChartImage;
    }

    public static StadiumEntity from(Stadium stadium) {
        return new StadiumEntity(
                stadium.getId(),
                stadium.getName(),
                stadium.getMainImage(),
                stadium.getSeatingChartImage(),
                stadium.getLabeledSeatingChartImage());
    }

    public Stadium toDomain() {
        return new Stadium(id, name, mainImage, seatingChartImage, labeledSeatingChartImage);
    }
}
