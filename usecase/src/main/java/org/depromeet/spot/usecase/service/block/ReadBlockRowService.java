package org.depromeet.spot.usecase.service.block;

import java.util.List;

import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.usecase.port.in.block.ReadBlockRowUsecase;
import org.depromeet.spot.usecase.port.out.block.BlockRowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadBlockRowService implements ReadBlockRowUsecase {

    private final BlockRowRepository blockRowRepository;

    @Override
    public List<BlockRow> findAllByBlock(Long blockId) {
        return blockRowRepository.findAllByBlock(blockId);
    }

    @Override
    public BlockRow findBy(long stadiumId, String blockCode, int rowNumber) {
        return blockRowRepository.findBy(stadiumId, blockCode, rowNumber);
    }
}
