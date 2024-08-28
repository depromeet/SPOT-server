package org.depromeet.spot.infrastructure.jpa.oauth.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.MemberRole;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor // 역직렬화를 위한 기본 생성자
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoEntity extends BaseEntity {

    private final String BASIC_PROFILE_IMAGE_URL =
            "https://spot-image-bucket-v2.s3.ap-northeast-2.amazonaws.com/profile-images/%EA%B8%B0%EB%B3%B8+%ED%94%84%EB%A1%9C%ED%95%84+%EC%9D%B4%EB%AF%B8%EC%A7%80.png";

    // 서비스에 연결 완료된 시각. UTC
    @JsonProperty("connected_at")
    public Date connectedAt;

    // 카카오 계정 정보
    @JsonProperty("kakao_account")
    public KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {

        // 사용자 프로필 정보
        @JsonProperty("profile")
        public Profile profile;

        // 이름 제공 동의 여부
        @JsonProperty("name_needs_agreement")
        public Boolean isNameAgree;

        // 카카오계정 이름
        @JsonProperty("name")
        public String name;

        // 이메일 제공 동의 여부
        @JsonProperty("email_needs_agreement")
        public Boolean isEmailAgree;

        // 이메일이 유효 여부
        // true : 유효한 이메일, false : 이메일이 다른 카카오 계정에 사용돼 만료
        @JsonProperty("is_email_valid")
        public Boolean isEmailValid;

        // 이메일이 인증 여부
        // true : 인증된 이메일, false : 인증되지 않은 이메일
        @JsonProperty("is_email_verified")
        public Boolean isEmailVerified;

        // 카카오계정 대표 이메일
        @JsonProperty("email")
        public String email;

        // 성별
        @JsonProperty("gender")
        public String gender;

        // 전화번호
        // +82 00-0000-0000 형식
        @JsonProperty("phone_number")
        public String phoneNumber;

        @Getter
        @NoArgsConstructor
        @ToString
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Profile {

            // 닉네임
            @JsonProperty("nickname")
            public String nickname;

            // 프로필 미리보기 이미지 URL
            @JsonProperty("thumbnail_image_url")
            public String thumbnailImageUrl;

            // 프로필 사진 URL
            @JsonProperty("profile_image_url")
            public String profileImageUrl;
        }
    }

    public Member toKakaoDomain(Member member) {
        return Member.builder()
                .email(kakaoAccount.email)
                .name(kakaoAccount.name)
                .nickname(member.getNickname())
                .phoneNumber(kakaoAccount.phoneNumber)
                .profileImage(BASIC_PROFILE_IMAGE_URL)
                .snsProvider(SnsProvider.KAKAO)
                .idToken(getId().toString())
                .role(MemberRole.ROLE_USER)
                .teamId(member.getTeamId())
                .createdAt(toLocalDateTime(connectedAt))
                .build();
    }

    public Member toLoginDomain() {
        return Member.builder().idToken(getId().toString()).build();
    }

    public LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }
}
