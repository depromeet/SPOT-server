package org.depromeet.spot.jpa.review.entity.keyword;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.keyword.BlockTopKeyword;
import org.depromeet.spot.jpa.block.entity.BlockEntity;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "block_top_keywords")
@NoArgsConstructor
@AllArgsConstructor
public class BlockTopKeywordEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "block_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BlockEntity block;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "keyword_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private KeywordEntity keyword;

    @Column(name = "count", nullable = false)
    private Long count;

    public static BlockTopKeywordEntity from(BlockTopKeyword blockTopKeyword) {
        return new BlockTopKeywordEntity(
                BlockEntity.from(blockTopKeyword.getBlock()),
                KeywordEntity.from(blockTopKeyword.getKeyword()),
                blockTopKeyword.getCount());
    }

    public BlockTopKeyword toDomain() {
        return BlockTopKeyword.builder()
                .id(this.getId())
                .block(block.toDomain())
                .keyword(keyword.toDomain())
                .count(count)
                .build();
    }
}
