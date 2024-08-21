package org.depromeet.spot.usecase.service.block;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.hashtag.HashTag;
import org.depromeet.spot.usecase.port.in.block.ReadBlockTagUsecase;
import org.depromeet.spot.usecase.port.out.block.BlockTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadBlockTagService implements ReadBlockTagUsecase {

    private final BlockTagRepository blockTagRepository;

    @Override
    public Map<HashTag, List<Block>> findAllByStadium(final Long stadiumId) {
        return blockTagRepository.findAllByStadium(stadiumId);
    }
}
