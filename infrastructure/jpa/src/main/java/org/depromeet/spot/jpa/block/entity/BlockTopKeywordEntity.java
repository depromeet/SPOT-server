package org.depromeet.spot.jpa.block.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.block.BlockTopKeyword;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "block_top_keywords")
@NoArgsConstructor
@AllArgsConstructor
public class BlockTopKeywordEntity extends BaseEntity {

    @Column(name = "block_id", nullable = false)
    private Long blockId;

    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    @Column(name = "count", nullable = false)
    private Long count;

    public static BlockTopKeywordEntity from(BlockTopKeyword blockTopKeyword) {
        return new BlockTopKeywordEntity(
                blockTopKeyword.getBlockId(),
                blockTopKeyword.getKeywordId(),
                blockTopKeyword.getCount());
    }

    public BlockTopKeyword toDomain() {
        return BlockTopKeyword.builder()
                .id(this.getId())
                .blockId(blockId)
                .keywordId(keywordId)
                .count(count)
                .build();
    }
}
