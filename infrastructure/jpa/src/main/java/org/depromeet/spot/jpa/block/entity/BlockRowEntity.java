package org.depromeet.spot.jpa.block.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "block_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BlockEntity block;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "max_seats", nullable = false)
    private Integer maxSeats;

    public static BlockRowEntity from(BlockRow blockRow) {
        return new BlockRowEntity(
                BlockEntity.from(blockRow.getBlock()),
                blockRow.getNumber(),
                blockRow.getMaxSeats());
    }

    public static BlockRowEntity withBlockRow(BlockRow blockRow) {
        return new BlockRowEntity(blockRow);
    }

    public BlockRowEntity(BlockRow blockRow) {
        super(blockRow.getId(), null, null, null);
        block = BlockEntity.withBlock(blockRow.getBlock());
        number = blockRow.getNumber();
        maxSeats = blockRow.getMaxSeats();
    }

    public BlockRow toDomain() {
        return new BlockRow(this.getId(), block.toDomain(), number, maxSeats);
    }
}
