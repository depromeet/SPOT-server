package org.depromeet.spot.infrastructure.jpa.block.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.block.BlockTag;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;
import org.depromeet.spot.infrastructure.jpa.hashtag.HashTagEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "block_tags")
public class BlockTagEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "block_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BlockEntity block;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "hashtag_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private HashTagEntity hashTag;

    public static BlockTagEntity from(BlockTag blockTag) {
        BlockEntity blockEntity = BlockEntity.withBlock(blockTag.getBlock());
        HashTagEntity hashTagEntity = HashTagEntity.from(blockTag.getHashTag());
        return new BlockTagEntity(blockEntity, hashTagEntity);
    }

    public BlockTag toDomain() {
        return new BlockTag(this.getId(), block.toDomain(), hashTag.toDomain());
    }
}
