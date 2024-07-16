package org.depromeet.spot.application.stadium.dto.response;

import org.depromeet.spot.domain.stadium.Stadium;

import lombok.Builder;

@Builder
public record StadiumResponse(
        Long id,
        String name,
        String mainImage,
        String seatingChartImage,
        String labeledSeatingChartImage,
        boolean isActive) {

    public static StadiumResponse from(Stadium stadium) {
        return StadiumResponse.builder()
                .id(stadium.getId())
                .name(stadium.getName())
                .mainImage(stadium.getMainImage())
                .seatingChartImage(builder().seatingChartImage)
                .labeledSeatingChartImage(builder().labeledSeatingChartImage)
                .isActive(builder().isActive)
                .build();
    }
}
