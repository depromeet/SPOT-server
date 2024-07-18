package org.depromeet.spot.jpa.block.repository;

import java.util.List;

import org.depromeet.spot.jpa.block.entity.BlockRowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlockRowJpaRepository extends JpaRepository<BlockRowEntity, Long> {

    List<BlockRowEntity> findAllByBlockIdOrderByNumberAsc(Long blockId);

    @Query(
            "select r from BlockRowEntity r "
                    + "where r.block.stadiumId = :stadiumId "
                    + "and r.block.code = :code "
                    + "order by r.number asc")
    List<BlockRowEntity> findAllByStadiumAndBlock(
            @Param("stadiumId") Long stadiumId, @Param("code") String code);
}
