package org.depromeet.spot.application.block.dto.response;

import java.util.List;

import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase.BlockInfo;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase.RowInfo;

public record BlockInfoResponse(Long id, String code, List<RowInfoResponse> rowInfo) {
    public static BlockInfoResponse from(BlockInfo blockInfo) {
        List<RowInfo> rowInfos = blockInfo.getRowInfo();
        List<RowInfoResponse> rowInfoResponses =
                rowInfos.stream().map(RowInfoResponse::from).toList();
        return new BlockInfoResponse(blockInfo.getId(), blockInfo.getCode(), rowInfoResponses);
    }
}
