package org.depromeet.spot.usecase.port.in.member;

import org.depromeet.spot.domain.member.Member;
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
        private final String mascotImageUrl;
        private String teamImageUrl;
        private final Long teamId;
        private final String teamName;
        private final Long reviewCntToLevelUp;

        public static MemberInfo of(
                Member member, BaseballTeam baseballTeam, Long reviewCntToLevelUp) {
            return MemberInfo.builder()
                    .nickname(member.getNickname())
                    .profileImageUrl(member.getProfileImage())
                    .level(member.getLevel().getValue())
                    .levelTitle(member.getLevel().getTitle())
                    .mascotImageUrl(member.getLevel().getMascotImageUrl())
                    .teamImageUrl(baseballTeam.getLogo())
                    .teamId(baseballTeam.getId())
                    .teamName(baseballTeam.getName())
                    .reviewCntToLevelUp(reviewCntToLevelUp)
                    .build();
        }
    }
}
