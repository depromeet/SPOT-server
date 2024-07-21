package org.depromeet.spot.usecase.port.in.member;

import org.depromeet.spot.domain.member.Member;

public interface MemberUsecase {

    Member create(String accessToken, Member member);

    Member login(String idCode);

    Boolean duplicatedNickname(String nickname);

    String getAccessToken(String idCode);

    Boolean deleteMember(String accessToken);
}
