package org.depromeet.spot.infrastructure.jpa.oauth.entity;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.MemberRole;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 역직렬화를 위한 기본 생성자
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserInfoEntity extends BaseEntity {

    private final String BASIC_PROFILE_IMAGE_URL =
            "https://spot-image-bucket-v2.s3.ap-northeast-2.amazonaws.com/profile-images/%EA%B8%B0%EB%B3%B8+%ED%94%84%EB%A1%9C%ED%95%84+%EC%9D%B4%EB%AF%B8%EC%A7%80.png";

    //    구글 로그인은 sub라는 이름으로 id값을 줌.
    //    구글의 sub 값은 Long 타입을 넘어감.
    //    BigInteger로 처리하거나 String으로 처리해야함.
    @JsonProperty("sub")
    public String idToken;

    // 풀네임(닉네임)
    @JsonProperty("name")
    public String nickname;

    // 구글 이메일
    @JsonProperty("email")
    public String email;

    // 프로필사진
    @JsonProperty("picture")
    public String profileImageUrl;

    public Member toGoogleDomain(Member member) {
        return Member.builder()
                .email(email)
                .nickname(member.getNickname())
                .profileImage(BASIC_PROFILE_IMAGE_URL)
                .snsProvider(SnsProvider.GOOGLE)
                .idToken(idToken)
                .role(MemberRole.ROLE_USER)
                .teamId(member.getTeamId())
                .build();
    }

    public Member toLoginDomain() {
        return Member.builder().idToken(idToken).build();
    }
}
