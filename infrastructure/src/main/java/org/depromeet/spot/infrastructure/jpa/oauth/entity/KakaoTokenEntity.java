package org.depromeet.spot.infrastructure.jpa.oauth.entity;

import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 역직렬화를 위한 기본 생성자
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoTokenEntity extends BaseEntity {

    @JsonProperty("token_type")
    public String tokenType;

    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("id_token")
    public String idToken;

    @JsonProperty("expires_in")
    public Integer expiresIn;

    @JsonProperty("refresh_token")
    public String refreshToken;

    @JsonProperty("refresh_token_expires_in")
    public Integer refreshTokenExpiresIn;

    @JsonProperty("scope")
    public String scope;
}
