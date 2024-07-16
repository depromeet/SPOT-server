package org.depromeet.spot.domain.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BlockRow {

    private final Long id;
    private final Long blockId;
    private final Integer number;
    private final Integer maxSeats;
}
