package org.depromeet.spot.usecase.port.out.oauth;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.SnsProvider;

public interface OauthRepository {

    String getKakaoAccessToken(String authorizationCode);

    String getOauthAccessToken(SnsProvider snsProvider, String authorizationCode);

    Member getKakaoRegisterUserInfo(String accessToken, Member member);

    Member getOauthRegisterUserInfo(String accessToken, Member member);

    Member getLoginUserInfo(String accessToken);

    Member getOauthLoginUserInfo(SnsProvider snsProvider, String accessToken);
}
