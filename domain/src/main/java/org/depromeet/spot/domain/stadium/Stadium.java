package org.depromeet.spot.domain.stadium;

import lombok.Getter;

@Getter
public class Stadium {

    public final Long id;
    public final String name;
    public final String mainImage;
    public final String seatingChartImage;
    public final String labeledSeatingChartImage;

    public Stadium(
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
}
