package org.depromeet.spot.jpa.review.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.jpa.review.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
    long countByMemberId(Long memberId);

    @Query(
            "SELECT r FROM ReviewEntity r WHERE r.stadium.id = :stadiumId AND r.block.code = :blockCode "
                    + "AND (:rowNumber IS NULL OR r.row.number = :rowNumber) "
                    + "AND (:seatNumber IS NULL OR r.seat.seatNumber = :seatNumber) "
                    + "AND (:year IS NULL OR YEAR(r.dateTime) = :year) "
                    + "AND (:month IS NULL OR MONTH(r.dateTime) = :month) "
                    + "AND r.deletedAt IS NULL")
    Page<ReviewEntity> findByStadiumIdAndBlockCode(
            @Param("stadiumId") Long stadiumId,
            @Param("blockCode") String blockCode,
            @Param("rowNumber") Integer rowNumber,
            @Param("seatNumber") Integer seatNumber,
            @Param("year") Integer year,
            @Param("month") Integer month,
            Pageable pageable);

    @Query(
            "SELECT r FROM ReviewEntity r WHERE r.member.id = :userId "
                    + "AND (:year IS NULL OR YEAR(r.dateTime) = :year) "
                    + "AND (:month IS NULL OR MONTH(r.dateTime) = :month) "
                    + "AND r.deletedAt IS NULL")
    Page<ReviewEntity> findByUserId(
            @Param("userId") Long userId,
            @Param("year") Integer year,
            @Param("month") Integer month,
            Pageable pageable);

    @Query(
            "SELECT new org.depromeet.spot.domain.review.ReviewYearMonth(YEAR(r.dateTime), MONTH(r.dateTime)) "
                    + "FROM ReviewEntity r WHERE r.member.id = :memberId "
                    + "AND r.deletedAt IS NULL "
                    + "GROUP BY YEAR(r.dateTime), MONTH(r.dateTime) "
                    + "ORDER BY YEAR(r.dateTime) DESC, MONTH(r.dateTime) DESC")
    List<ReviewYearMonth> findReviewMonthsByMemberId(@Param("memberId") Long memberId);

    @Modifying
    @Query(
            "UPDATE ReviewEntity r SET r.deletedAt = :deletedAt WHERE r.id = :reviewId AND r.member.id = :memberId AND r.deletedAt IS NULL")
    int softDeleteByIdAndMemberId(
            @Param("reviewId") Long reviewId,
            @Param("memberId") Long memberId,
            @Param("deletedAt") LocalDateTime deletedAt);

    @Query(
            "SELECT r FROM ReviewEntity r WHERE r.member.id = :memberId "
                    + "AND r.deletedAt IS NULL "
                    + "ORDER BY r.createdAt desc "
                    + "LIMIT 1")
    ReviewEntity findLastReviewByMemberId(@Param("memberId") Long memberId);

    @Query(
            "SELECT count(r) FROM ReviewEntity r WHERE r.member.id = :memberId "
                    + "AND r.deletedAt IS NULL")
    long countByIdByMemberId(@Param("memberId") Long memberId);
}
