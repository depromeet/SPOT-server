package org.depromeet.spot.application.team.dto.request;

import jakarta.validation.constraints.NotBlank;

import org.depromeet.spot.application.common.dto.RgbCodeRequest;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.hibernate.validator.constraints.Length;

public record CreateBaseballTeamReq(
        @NotBlank(message = "구단명을 입력해주세요.")
                @Length(max = 20, message = "구단명은 최대 20글자 까지만 입력할 수 있습니다.")
                String name,
        @NotBlank(message = "구단 별칭을 입력해주세요.")
                @Length(max = 10, message = "구단 별칭은 최대 10글자 까지만 입력할 수 있습니다.")
                String alias,
        @NotBlank(message = "구단 로고를 입력해주세요.") String logo,
        RgbCodeRequest rgbCode) {

    public BaseballTeam toDomain() {
        return BaseballTeam.builder()
                .name(name)
                .alias(alias)
                .logo(logo)
                .labelRgbCode(rgbCode.toDomain())
                .build();
    }
}
