package org.depromeet.spot.domain.block;

import org.depromeet.spot.domain.hashtag.HashTag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlockTag {

    private final Long id;
    private final Block block;
    private final HashTag hashTag;
}
