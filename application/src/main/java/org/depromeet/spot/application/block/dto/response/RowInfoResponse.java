package org.depromeet.spot.application.block.dto.response;

import java.util.List;

import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase.RowInfo;

import lombok.Builder;

@Builder
public record RowInfoResponse(Long id, int number, List<Integer> seatNumList) {

    public static RowInfoResponse from(RowInfo rowInfo) {
        return RowInfoResponse.builder()
                .id(rowInfo.getId())
                .number(rowInfo.getNumber())
                .seatNumList(rowInfo.getSeatNumList())
                .build();
    }
}
