package org.depromeet.spot.usecase.port.in.seat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface ReadSeatUsecase {

    List<BlockInfo> findAllBlockInfoBy(Long stadiumId, Long sectionId);

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
