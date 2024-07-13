package org.depromeet.spot.usecase.port.in.block;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface BlockReadUsecase {

    List<BlockCodeInfo> findCodeInfosByStadium(Long stadiumId, Long sectionId);

    @Getter
    @AllArgsConstructor
    class BlockCodeInfo {
        private final Long id;
        private final String code;
    }
}
