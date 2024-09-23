package org.depromeet.spot.infrastructure.jpa.member.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.depromeet.spot.infrastructure.jpa.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    @Query("select m from MemberEntity m where m.idToken = :idToken and m.deletedAt is null")
    Optional<MemberEntity> findByIdToken(@Param("idToken") String idToken);

    @Query("select m from MemberEntity m " + "join fetch m.level l " + "where m.id = :id")
    Optional<MemberEntity> findByIdWithLevel(@Param("id") Long id);

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
