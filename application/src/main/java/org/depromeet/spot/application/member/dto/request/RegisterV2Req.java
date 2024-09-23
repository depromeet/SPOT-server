package org.depromeet.spot.application.member.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterV2Req(
        @NotNull(message = "인가 accessToken는 필수 값입니다.") @Schema(description = "카카오 인증 accessToken")
                String accessToken,
        @NotNull(message = "닉네임 값은 필수입니다.")
                @Schema(description = "설정하려는 닉네임")
                @Length(min = 2, max = 10, message = "닉네임은 2글자에서 10글자 사이여야합니다.")
                @Pattern(
                        regexp = "^[a-zA-Z0-9가-힣]*$",
                        message = "닉네임은 알파벳 대소문자, 숫자, 한글만 허용하며, 공백은 불가능합니다.")
                String nickname,
        @Schema(description = "응원 팀 pk")
                @Range(
                        min = 1,
                        max = 10,
                        message = "응원 팀은 null(모두 응원), 1번(두산 베어스)부터 10번(NC 다이노스)까지 입니다.")
                Long teamId,
        @NotNull(message = "SNS Provider는 필수 값입니다.") @Schema(description = "KAKAO/GOOGLE")
                SnsProvider snsProvider) {

    public Member toDomain() {
        return Member.builder().nickname(nickname).teamId(teamId).snsProvider(snsProvider).build();
    }
}
