package org.depromeet.spot.application.sample.controller.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record SampleRequest(
    @NotBlank(message = "메세지는 공백일 수 없습니다.")
    @Length(min = 1, max = 30, message = "메세지는 1~30글자 사이여야 합니다.")
    String message
) {

}
