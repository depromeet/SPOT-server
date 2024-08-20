package org.depromeet.spot.application.stadium.dto.response;

import java.util.List;

import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase.StadiumBlockTagInfo;

public record StadiumBlockTagResponse(
        long id, String name, List<String> blockCodes, String description) {
    public static StadiumBlockTagResponse from(StadiumBlockTagInfo info) {
        return new StadiumBlockTagResponse(
                info.getId(), info.getName(), info.getBlockCodes(), info.getDescription());
    }
}
