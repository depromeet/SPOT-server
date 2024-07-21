package org.depromeet.spot.usecase.port.in.block;

import java.util.List;

import org.depromeet.spot.domain.block.Block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface BlockReadUsecase {

    List<BlockCodeInfo> findCodeInfosByStadium(Long stadiumId, Long sectionId);

    List<BlockInfo> findAllBlockInfoBy(final Long stadiumId, final Long sectionId);

    BlockInfo findBlockInfoBy(final Long stadiumId, final String blockCode);

    Block findById(Long blockId);

    Block findByStadiumAndCode(Long stadiumId, String code);

    void checkIsDuplicateCode(Long stadiumId, String code);

    boolean existsById(Long blockId);

    void checkExistsById(Long blockId);

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
        private List<Integer> seatNumList;
    }
}
