package org.depromeet.spot.usecase.port.in.member;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.Level;
import org.depromeet.spot.domain.team.BaseballTeam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface MemberUsecase {

    Member create(String accessToken, Member member);

    Member login(String idCode);

    boolean duplicatedNickname(String nickname);

    String getAccessToken(String idCode);

    boolean deleteMember(String accessToken);

    MemberInfo findMemberInfo(Long memberId);

    void softDelete(Long memberId);

    @Getter
    @Builder
    @AllArgsConstructor
    class MemberInfo {
        private final String nickname;
        private final String profileImageUrl;
        private final int level;
        private final String levelTitle;
        private String teamImageUrl;

        public static MemberInfo of(Member member, BaseballTeam baseballTeam) {
            final int level = member.getLevel();
            return MemberInfo.builder()
                    .nickname(member.getNickname())
                    .profileImageUrl(member.getProfileImage())
                    .level(level)
                    .levelTitle(Level.getTitleFrom(level))
                    .teamImageUrl(baseballTeam.getLogo())
                    .build();
        }
    }
}
