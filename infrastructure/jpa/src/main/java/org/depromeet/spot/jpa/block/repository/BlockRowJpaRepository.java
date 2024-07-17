package org.depromeet.spot.jpa.block.repository;

import java.util.List;

import org.depromeet.spot.jpa.block.entity.BlockRowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRowJpaRepository extends JpaRepository<BlockRowEntity, Long> {

    List<BlockRowEntity> findAllByBlockIdOrderByNumberAsc(Long blockId);
}
