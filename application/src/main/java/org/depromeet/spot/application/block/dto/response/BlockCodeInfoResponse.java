package org.depromeet.spot.application.block.dto.response;

import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase.BlockCodeInfo;

public record BlockCodeInfoResponse(Long id, String code) {

    public static BlockCodeInfoResponse from(BlockCodeInfo blockCodeInfo) {
        return new BlockCodeInfoResponse(blockCodeInfo.getId(), blockCodeInfo.getCode());
    }
}
