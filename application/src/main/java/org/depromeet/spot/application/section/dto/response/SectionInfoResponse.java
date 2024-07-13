package org.depromeet.spot.application.section.dto.response;

import org.depromeet.spot.domain.common.RgbCode;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase.SectionInfo;

import lombok.Builder;

@Builder
public record SectionInfoResponse(Long id, String name, String alias, RgbCode color) {

    public static SectionInfoResponse from(SectionInfo sectionInfo) {
        return SectionInfoResponse.builder()
                .id(sectionInfo.getId())
                .name(sectionInfo.getName())
                .alias(sectionInfo.getAlias())
                .color(sectionInfo.getColor())
                .build();
    }
}
