package org.depromeet.spot.jpa.block.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.block.BlockRow;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "block_rows")
@NoArgsConstructor
public class BlockRowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "block_id", nullable = false)
    private Long blockId;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "max_seats", nullable = false)
    private Long maxSeats;

    public BlockRowEntity(Long id, Long blockId, Long number, Long maxSeats) {
        this.id = id;
        this.blockId = blockId;
        this.number = number;
        this.maxSeats = maxSeats;
    }

    public static BlockRowEntity from(BlockRow blockRow) {
        return new BlockRowEntity(
                blockRow.getId(),
                blockRow.getBlockId(),
                blockRow.getNumber(),
                blockRow.getMaxSeats());
    }

    public BlockRow toDomain() {
        return new BlockRow(id, blockId, number, maxSeats);
    }
}
