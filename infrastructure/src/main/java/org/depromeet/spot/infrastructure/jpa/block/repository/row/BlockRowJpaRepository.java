package org.depromeet.spot.infrastructure.jpa.block.repository.row;

import java.util.List;

import org.depromeet.spot.infrastructure.jpa.block.entity.BlockRowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlockRowJpaRepository extends JpaRepository<BlockRowEntity, Long> {

    List<BlockRowEntity> findAllByBlockIdOrderByNumberAsc(Long blockId);

    @Query(
            "select r from BlockRowEntity r "
                    + "join BlockEntity b on b.id = r.block.id "
                    + "where b.stadiumId = :stadiumId and b.code = :blockCode and r.number = :rowNumber")
    BlockRowEntity findByBlockAndNumber(
            @Param("stadiumId") Long stadiumId,
            @Param("blockCode") String blockCode,
            @Param("rowNumber") int rowNumber);

    @Query(
            "select r from BlockRowEntity r "
                    + "join BlockEntity b on b.id = r.block.id "
                    + "where r.block.stadiumId = :stadiumId "
                    + "and r.block.code = :code "
                    + "order by r.number asc")
    List<BlockRowEntity> findAllByStadiumAndBlock(
            @Param("stadiumId") Long stadiumId, @Param("code") String code);
}
