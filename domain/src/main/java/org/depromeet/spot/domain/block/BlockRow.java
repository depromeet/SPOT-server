package org.depromeet.spot.domain.block;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BlockRow {

    private final Long id;
    private final Block block;
    private final Integer number;
    private final Integer maxSeats;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BlockRow row = (BlockRow) o;
        return Objects.equals(id, row.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
