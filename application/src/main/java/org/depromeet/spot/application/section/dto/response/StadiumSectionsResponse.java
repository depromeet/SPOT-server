package org.depromeet.spot.application.section.dto.response;

import java.util.List;

import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase.StadiumSections;

public record StadiumSectionsResponse(String seatChart, List<SectionInfoResponse> sectionList) {

    public static StadiumSectionsResponse from(StadiumSections stadiumSections) {
        List<SectionInfoResponse> infos =
                stadiumSections.getSectionList().stream().map(SectionInfoResponse::from).toList();
        return new StadiumSectionsResponse(stadiumSections.getSeatChart(), infos);
    }
}
