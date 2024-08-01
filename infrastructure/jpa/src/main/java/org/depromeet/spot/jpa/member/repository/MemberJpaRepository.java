package org.depromeet.spot.jpa.member.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.depromeet.spot.jpa.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByIdToken(String idToken);

    boolean existsByNickname(String nickname);

    @Modifying
    @Query(
            "update MemberEntity m set "
                    + "m.nickname = :nickname, "
                    + "m.profileImage = :profileImage, "
                    + "m.teamId = :teamId "
                    + "where m.id = :memberId")
    void updateProfile(
            @Param("memberId") Long memberId,
            @Param("profileImage") String profileImage,
            @Param("teamId") Long teamId,
            @Param("nickname") String nickname);

    @Modifying
    @Query("update MemberEntity m set m.level = :levelId where m.id = :memberId")
    void updateLevel(@Param("memberId") Long memberId, @Param("levelId") Long levelId);

    void deleteByIdToken(String idToken);

    @Modifying
    @Query("update MemberEntity m set m.deletedAt = :deletedAt where m.id = :memberId")
    void updateDeletedAt(
            @Param("memberId") Long memberId, @Param("deletedAt") LocalDateTime deletedAt);

    @Modifying
    @Query(
            "update MemberEntity m set m.deletedAt = null, m.updatedAt = :updatedAt where m.id = :memberId")
    void updateDeletedAtAndUpdatedAt(
            @Param("memberId") Long memberId, @Param("updatedAt") LocalDateTime updatedAt);
}
