package org.depromeet.spot.usecase.service.block;

import java.util.List;

import org.depromeet.spot.common.exception.stadium.StadiumException.StadiumNotFoundException;
import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.out.block.BlockRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockReadService implements BlockReadUsecase {

    private final BlockRepository blockRepository;
    private final StadiumReadUsecase stadiumReadUsecase;

    @Override
    public List<BlockCodeInfo> findCodeInfosByStadium(final Long stadiumId) {
        if (!stadiumReadUsecase.existsById(stadiumId)) {
            throw new StadiumNotFoundException();
        }
        List<Block> blocks = blockRepository.findAllByStadium(stadiumId);
        return blocks.stream().map(b -> new BlockCodeInfo(b.getId(), b.getCode())).toList();
    }
}
