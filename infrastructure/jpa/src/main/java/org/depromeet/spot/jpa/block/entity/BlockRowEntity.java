package org.depromeet.spot.jpa.block.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "block_rows")
@NoArgsConstructor
@AllArgsConstructor
public class BlockRowEntity extends BaseEntity {
    @Column(name = "block_id", nullable = false)
    private Long blockId;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "max_seats", nullable = false)
    private Long maxSeats;

    public static BlockRowEntity from(BlockRow blockRow) {
        return new BlockRowEntity(
                blockRow.getBlockId(), blockRow.getNumber(), blockRow.getMaxSeats());
    }

    public BlockRow toDomain() {
        return new BlockRow(this.getId(), blockId, number, maxSeats);
    }
}
