package org.depromeet.spot.infrastructure.jpa.block.repository.tag;

import org.depromeet.spot.infrastructure.jpa.block.entity.BlockTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockTagJpaRepository extends JpaRepository<BlockTagEntity, Long> {}
