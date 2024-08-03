package org.depromeet.spot.infrastructure.jpa.block.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blocks")
@NoArgsConstructor
@AllArgsConstructor
public class BlockEntity extends BaseEntity {

    @Column(name = "stadium_id", nullable = false)
    private Long stadiumId;

    @Column(name = "section_id", nullable = false)
    private Long sectionId;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "max_rows", nullable = false)
    private Integer maxRows;

    public static BlockEntity from(Block block) {
        return new BlockEntity(
                block.getStadiumId(), block.getSectionId(), block.getCode(), block.getMaxRows());
    }

    public static BlockEntity withBlock(Block block) {
        return new BlockEntity(block);
    }

    public BlockEntity(Block block) {
        super(block.getId(), null, null, null);
        stadiumId = block.getStadiumId();
        sectionId = block.getSectionId();
        code = block.getCode();
        maxRows = block.getMaxRows();
    }

    public Block toDomain() {
        return new Block(this.getId(), stadiumId, sectionId, code, maxRows);
    }
}
