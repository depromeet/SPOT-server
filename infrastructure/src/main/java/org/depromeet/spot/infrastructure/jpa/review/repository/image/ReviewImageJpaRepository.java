package org.depromeet.spot.infrastructure.jpa.review.repository.image;

import java.util.List;

import org.depromeet.spot.infrastructure.jpa.review.dto.TopReviewImageDto;
import org.depromeet.spot.infrastructure.jpa.review.entity.image.ReviewImageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewImageJpaRepository extends JpaRepository<ReviewImageEntity, Long> {
    @Query(
            "SELECT new org.depromeet.spot.infrastructure.jpa.review.dto.TopReviewImageDto(ri.url, r.id, b.code, br.number, s.seatNumber) "
                    + "FROM ReviewImageEntity ri "
                    + "JOIN ri.review r "
                    + "JOIN r.block b "
                    + "JOIN r.row br "
                    + "JOIN r.seat s "
                    + "WHERE b.stadiumId = :stadiumId AND b.code = :blockCode "
                    + "ORDER BY r.createdAt DESC")
    List<TopReviewImageDto> findTopReviewImagesByStadiumIdAndBlockCode(
            @Param("stadiumId") Long stadiumId,
            @Param("blockCode") String blockCode,
            Pageable pageable);
}
