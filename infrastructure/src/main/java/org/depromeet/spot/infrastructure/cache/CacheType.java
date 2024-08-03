package org.depromeet.spot.infrastructure.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheType {
    SECTION_SEATS("sectionSeatsCache", 100L, 60 * 60 * 24L),
    BLOCK_SEATS("blockSeatsCache", 1_000L, 60 * 60 * 24L),
    LEVEL_INFO("levelsCache", 1L, 60 * 60 * 1L),
    ;

    private final String name;
    private final Long size;
    private final Long expireSeconds;
}
