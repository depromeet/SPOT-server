package org.depromeet.spot.usecase.port.in.oauth;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.depromeet.spot.domain.team.BaseballTeam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface OauthUsecase {

    Member create(String accessToken, Member member);

    Member login(SnsProvider snsProvider, String token);

    String getOauthAccessToken(SnsProvider snsProvider, String authorizationCode);

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

        public static MemberInfo of(Member member, Long reviewCntToLevelUp) {
            return MemberInfo.builder()
                    .nickname(member.getNickname())
                    .profileImageUrl(member.getProfileImage())
                    .level(member.getLevel().getValue())
                    .levelTitle(member.getLevel().getTitle())
                    .mascotImageUrl(member.getLevel().getMascotImageUrl())
                    .teamImageUrl(null)
                    .teamId(null)
                    .teamName(null)
                    .reviewCntToLevelUp(reviewCntToLevelUp)
                    .build();
        }

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
