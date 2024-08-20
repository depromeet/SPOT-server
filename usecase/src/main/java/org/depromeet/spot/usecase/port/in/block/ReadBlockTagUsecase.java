package org.depromeet.spot.usecase.port.in.block;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.hashtag.HashTag;

public interface ReadBlockTagUsecase {

    Map<HashTag, List<Block>> findAllByStadium(Long stadiumId);
}
