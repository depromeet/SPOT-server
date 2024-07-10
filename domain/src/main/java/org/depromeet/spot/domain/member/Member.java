package org.depromeet.spot.domain.member;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Member {

    private final Long userId;
    private final String email;
    private final String name;
    private final String nickname;
    private final String phoneNumber;
    private final Integer level;
    private final String profileImage;
    private final String snsProvider;
    private final String idToken;
    private final String myTeam;
    private final Integer role;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    public Member(
            Long userId,
            String email,
            String name,
            String nickname,
            String phoneNumber,
            Integer level,
            String profileImage,
            String snsProvider,
            String idToken,
            String myTeam,
            Integer role,
            LocalDateTime createdAt,
            LocalDateTime deletedAt) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.level = level;
        this.profileImage = profileImage;
        this.snsProvider = snsProvider;
        this.idToken = idToken;
        this.myTeam = myTeam;
        this.role = role;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
