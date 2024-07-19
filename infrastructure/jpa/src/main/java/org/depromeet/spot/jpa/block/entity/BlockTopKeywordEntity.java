package org.depromeet.spot.jpa.block.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.depromeet.spot.jpa.common.entity.BaseEntity;
import org.depromeet.spot.jpa.review.entity.KeywordEntity;

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

    //    public static BlockTopKeywordEntity from(BlockTopKeyword blockTopKeyword) {
    //        return new BlockTopKeywordEntity(
    //                blockTopKeyword.getBlockId(),
    //                blockTopKeyword.getKeywordId(),
    //                blockTopKeyword.getCount());
    //    }
    //
    //    public BlockTopKeyword toDomain() {
    //        return BlockTopKeyword.builder()
    //                .id(this.getId())
    //                .blockId(blockId)
    //                .keywordId(keywordId)
    //                .count(count)
    //                .build();
    //    }
}
