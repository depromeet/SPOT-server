package org.depromeet.spot.jpa.member.repository;

import org.depromeet.spot.jpa.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByIdToken(String idToken);

    Boolean existsByNickname(String nickname);
}
