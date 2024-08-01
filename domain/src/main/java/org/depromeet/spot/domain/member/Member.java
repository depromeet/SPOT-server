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
    private final Integer level;
    private final String profileImage;
    private final SnsProvider snsProvider;
    private final String idToken;
    private final Long teamId;
    private final MemberRole role;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime updatedAt;

    public int calculateLevel(long reviewCnt) {
        if (reviewCnt == 0) {
            return 0;
        }
        if (reviewCnt <= 2) {
            return 1;
        }
        if (2 < reviewCnt && reviewCnt <= 4) {
            return 2;
        }
        if (4 < reviewCnt && reviewCnt <= 7) {
            return 3;
        }
        if (7 < reviewCnt && reviewCnt <= 13) {
            return 4;
        }
        if (13 < reviewCnt && reviewCnt <= 20) {
            return 5;
        }
        return 6;
    }

    public Member updateLevel(int newLevel) {
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

    public long calculateReviewCntToLevelUp(long reviewCnt) {
        int nextLevel;
        if (reviewCnt == 0) {
            nextLevel = 1;
        } else if (reviewCnt <= 2) {
            nextLevel = 3;
        } else if (2 < reviewCnt && reviewCnt <= 4) {
            nextLevel = 5;
        } else if (4 < reviewCnt && reviewCnt <= 7) {
            nextLevel = 8;
        } else if (7 < reviewCnt && reviewCnt <= 13) {
            nextLevel = 14;
        } else if (13 < reviewCnt && reviewCnt <= 20) {
            nextLevel = 21;
        } else return 0;
        return nextLevel - reviewCnt;
    }
}
