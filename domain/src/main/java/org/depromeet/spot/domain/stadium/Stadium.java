package org.depromeet.spot.domain.stadium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Stadium {

    private final Long id;
    private final String name;
    private final String mainImage;
    private final String seatingChartImage;
    private final String labeledSeatingChartImage;
    private final boolean isActive;
}
