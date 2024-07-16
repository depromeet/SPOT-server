package org.depromeet.spot.usecase.port.out.oauth;

import org.depromeet.spot.domain.member.Member;

public interface OauthRepository {

    String getKakaoAccessToken(String idCode);

    Member getUserInfo(String accesstoken, Member member);

}
