package org.depromeet.spot.usecase.port.out.oauth;

import org.depromeet.spot.domain.member.Member;

public interface OauthRepository {

    String getKakaoAccessToken(String idCode);

    Member getRegisterUserInfo(String accesstoken, Member member);

    Member getLoginUserInfo(String accesstoken);
}
