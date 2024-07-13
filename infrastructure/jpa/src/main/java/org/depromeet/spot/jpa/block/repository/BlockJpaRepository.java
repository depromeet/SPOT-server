package org.depromeet.spot.jpa.block.repository;

import java.util.List;

import org.depromeet.spot.jpa.block.entity.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockJpaRepository extends JpaRepository<BlockEntity, Long> {

    List<BlockEntity> findAllBySectionId(Long sectionId);
}
