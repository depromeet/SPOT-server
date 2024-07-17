package org.depromeet.spot.application.block.dto.response;

import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase.RowInfo;

import lombok.Builder;

@Builder
public record RowInfoResponse(Long id, int number, int minSeatNum, int maxSeatNum) {

    public static RowInfoResponse from(RowInfo rowInfo) {
        return RowInfoResponse.builder()
                .id(rowInfo.getId())
                .number(rowInfo.getNumber())
                .minSeatNum(rowInfo.getMinSeatNum())
                .maxSeatNum(rowInfo.getMaxSeatNum())
                .build();
    }
}
