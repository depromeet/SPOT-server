package org.depromeet.spot.domain.member;

import java.time.LocalDateTime;

import org.depromeet.spot.domain.member.enums.MemberRole;
import org.depromeet.spot.domain.member.enums.SnsProvider;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {

    private final Long id;
    private final String email;
    private final String name;
    private final String nickname;
    private final String phoneNumber;
    private final Integer level;
    private final String profileImage;
    private final SnsProvider snsProvider;
    private final String idToken;
    private final Long teamId;
    private final MemberRole role;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    public Member(
            Long id,
            String email,
            String name,
            String nickname,
            String phoneNumber,
            Integer level,
            String profileImage,
            SnsProvider snsProvider,
            String idToken,
            Long teamId,
            MemberRole role,
            LocalDateTime createdAt,
            LocalDateTime deletedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.level = level;
        this.profileImage = profileImage;
        this.snsProvider = snsProvider;
        this.idToken = idToken;
        this.teamId = teamId;
        this.role = role;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
