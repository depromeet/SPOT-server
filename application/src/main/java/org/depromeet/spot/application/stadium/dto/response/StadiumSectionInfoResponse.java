package org.depromeet.spot.application.stadium.dto.response;

import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase.StadiumSectionInfo;

public record StadiumSectionInfoResponse(long id, String name, String alias) {
    public static StadiumSectionInfoResponse from(StadiumSectionInfo info) {
        return new StadiumSectionInfoResponse(info.getId(), info.getName(), info.getAlias());
    }
}
