package org.depromeet.spot.usecase.port.in.block;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface BlockReadUsecase {

    List<BlockCodeInfo> findCodeInfosByStadium(Long stadiumId, Long sectionId);

    List<BlockInfo> findAllBlockInfoBy(final Long stadiumId, final Long sectionId);

    @Getter
    @AllArgsConstructor
    class BlockCodeInfo {
        private final Long id;
        private final String code;
    }

    @Getter
    @AllArgsConstructor
    class BlockInfo {
        private Long id;
        private String code;
        private List<RowInfo> rowInfo;
    }

    @Getter
    @Builder
    class RowInfo {
        private Long id;
        private int number;
        private int minSeatNum;
        private int maxSeatNum;
    }
}