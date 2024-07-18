package org.depromeet.spot.jpa.block.repository;

import java.util.List;
import java.util.Optional;

import org.depromeet.spot.jpa.block.entity.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockJpaRepository extends JpaRepository<BlockEntity, Long> {
    Optional<BlockEntity> findByCode(String code);

    List<BlockEntity> findAllBySectionId(Long sectionId);
}
