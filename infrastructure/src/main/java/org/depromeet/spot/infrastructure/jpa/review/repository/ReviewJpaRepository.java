package org.depromeet.spot.infrastructure.jpa.review.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;

import org.depromeet.spot.domain.review.Review.ReviewType;
import org.depromeet.spot.domain.review.ReviewCount;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.infrastructure.jpa.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
    long countByMemberIdAndDeletedAtIsNull(Long memberId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("Select r FROM ReviewEntity r WHERE r.id = :id")
    Optional<ReviewEntity> findByIdWithOptimistic(@Param("id") Long id);

    @Query(
            "SELECT new org.depromeet.spot.domain.review.ReviewYearMonth(YEAR(r.dateTime), MONTH(r.dateTime)) "
                    + "FROM ReviewEntity r WHERE r.member.id = :memberId "
                    + "AND r.deletedAt IS NULL "
                    + "AND r.reviewType = :reviewType "
                    + "GROUP BY YEAR(r.dateTime), MONTH(r.dateTime) "
                    + "ORDER BY YEAR(r.dateTime) DESC, MONTH(r.dateTime) DESC")
    List<ReviewYearMonth> findReviewMonthsByMemberId(
            @Param("memberId") Long memberId, @Param("reviewType") ReviewType reviewType);

    @Query(
            "SELECT new org.depromeet.spot.domain.review.ReviewCount("
                    + "COUNT(r), "
                    + "COALESCE(SUM(CASE WHEN r.reviewType = :viewType THEN r.likesCount ELSE 0 END), 0)) "
                    + "FROM ReviewEntity r "
                    + "WHERE r.member.id = :memberId AND r.deletedAt IS NULL")
    ReviewCount countAndSumLikesByMemberId(
            @Param("memberId") Long memberId, @Param("viewType") ReviewType viewType);

    @Modifying
    @Query(
            "UPDATE ReviewEntity r SET r.deletedAt = :deletedAt WHERE r.id = :reviewId AND r.member.id = :memberId AND r.deletedAt IS NULL")
    int softDeleteByIdAndMemberId(
            @Param("reviewId") Long reviewId,
            @Param("memberId") Long memberId,
            @Param("deletedAt") LocalDateTime deletedAt);

    @Modifying
    @Query(
            "UPDATE ReviewEntity r SET r.deletedAt = :deletedAt WHERE r.member.id = :memberId AND r.deletedAt IS NULL")
    void softDeleteAllReviewOwnedByMemberId(
            @Param("memberId") Long memberId, @Param("deletedAt") LocalDateTime deletedAt);

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

    @Modifying
    @Query("update ReviewEntity r set r.likesCount = :likesCount where r.id = :id")
    void updateLikesCount(@Param("id") long reviewId, @Param("likesCount") int likesCount);

    @Modifying
    @Query("update ReviewEntity r set r.scrapsCount = :scrapsCount where r.id = :id")
    void updateScrapsCount(@Param("id") long reviewId, @Param("scrapsCount") int scrapsCount);
}
