package org.depromeet.spot.application.section.dto.request;

import jakarta.validation.constraints.NotBlank;

import org.depromeet.spot.usecase.port.in.section.CreateSectionUsecase.CreateSectionCommand;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.Parameter;

public record CreateSectionRequest(
        @NotBlank
                @Parameter(description = "구역 이름", example = "오렌지석")
                @Length(max = 20, message = "구역 이름은 최대 20글자 까지만 입력할 수 있습니다.")
                String name,
        @Parameter(description = "구역 별칭", example = "응원석")
                @Length(max = 20, message = "별칭은 최대 20글자 까지만 입력할 수 있습니다.")
                String alias,
        @NotBlank String color) {

    public CreateSectionCommand toCommand() {
        return CreateSectionCommand.builder().name(name).alias(alias).labelColor(color).build();
    }
}
