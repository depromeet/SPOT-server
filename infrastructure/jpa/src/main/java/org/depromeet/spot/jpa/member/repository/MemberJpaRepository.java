package org.depromeet.spot.jpa.member.repository;

import java.util.Optional;

import org.depromeet.spot.jpa.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByIdToken(String idToken);

    Boolean existsByNickname(String nickname);

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
}
