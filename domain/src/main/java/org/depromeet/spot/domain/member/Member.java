package org.depromeet.spot.domain.member;

import java.time.LocalDateTime;

import org.depromeet.spot.domain.member.enums.MemberRole;
import org.depromeet.spot.domain.member.enums.SnsProvider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Member {

    private final Long id;
    private final String email;
    private final String name;
    private final String nickname;
    private final String phoneNumber;
    private final Level level;
    private final String profileImage;
    private final SnsProvider snsProvider;
    private final String idToken;
    private final Long teamId;
    private final MemberRole role;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime updatedAt;

    public Member updateLevel(Level newLevel) {
        return Member.builder()
                .id(id)
                .email(email)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .level(newLevel)
                .profileImage(profileImage)
                .snsProvider(snsProvider)
                .idToken(idToken)
                .teamId(teamId)
                .role(role)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .updatedAt(updatedAt)
                .build();
    }

    public Member updateProfile(String newProfileImage, String newNickname, Long newTeamId) {
        return Member.builder()
                .id(id)
                .email(email)
                .name(name)
                .nickname(newNickname)
                .phoneNumber(phoneNumber)
                .level(level)
                .profileImage(newProfileImage)
                .snsProvider(snsProvider)
                .idToken(idToken)
                .teamId(newTeamId)
                .role(role)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .updatedAt(updatedAt)
                .build();
    }
}
