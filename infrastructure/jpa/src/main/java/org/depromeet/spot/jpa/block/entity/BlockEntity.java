package org.depromeet.spot.jpa.block.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.block.Block;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "blocks")
@NoArgsConstructor
public class BlockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "stadium_id", nullable = false)
    private Long stadiumId;

    @Column(name = "section_id", nullable = false)
    private Long sectionId;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "max_rows", nullable = false)
    private Integer maxRows;

    public BlockEntity(Long id, Long stadiumId, Long sectionId, String code, Integer maxRows) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.sectionId = sectionId;
        this.code = code;
        this.maxRows = maxRows;
    }

    public static BlockEntity from(Block block) {
        return new BlockEntity(
                block.getId(),
                block.getStadiumId(),
                block.getSectionId(),
                block.getCode(),
                block.getMaxRows());
    }

    public Block toDomain() {
        return new Block(id, stadiumId, sectionId, code, maxRows);
    }
}
