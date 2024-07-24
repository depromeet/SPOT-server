package org.depromeet.spot.usecase.port.in.member;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.team.BaseballTeam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface MemberUsecase {

    Member create(String accessToken, Member member);

    Member login(String idCode);

    Boolean duplicatedNickname(String nickname);

    String getAccessToken(String idCode);

    Boolean deleteMember(String accessToken);

    MemberInfo findMemberInfo(Long memberId);

    void softDelete(Long memberId);

    @Getter
    @Builder
    @AllArgsConstructor
    class MemberInfo {
        Member member;
        BaseballTeam baseballTeam;

        public static MemberInfo of(Member member, BaseballTeam baseballTeam) {
            return MemberInfo.builder().member(member).baseballTeam(baseballTeam).build();
        }
    }
}
