package org.depromeet.spot.jpa.block.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.block.BlockTopKeyword;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "block_top_keywords")
@NoArgsConstructor
public class BlockTopKeywordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "block_id", nullable = false)
    private Long blockId;

    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    @Column(name = "count", nullable = false)
    private Long count;

    public BlockTopKeywordEntity(Long id, Long blockId, Long keywordId, Long count) {
        this.id = id;
        this.blockId = blockId;
        this.keywordId = keywordId;
        this.count = count;
    }

    public static BlockTopKeywordEntity from(BlockTopKeyword blockTopKeyword) {
        return new BlockTopKeywordEntity(
                blockTopKeyword.getId(),
                blockTopKeyword.getBlockId(),
                blockTopKeyword.getKeywordId(),
                blockTopKeyword.getCount());
    }

    public BlockTopKeyword toDomain() {
        return new BlockTopKeyword(id, blockId, keywordId, count);
    }
}
