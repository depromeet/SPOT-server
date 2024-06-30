package org.depromeet.spot.application.sample.controller;

import org.depromeet.spot.application.sample.controller.request.SampleRequest;
import org.depromeet.spot.application.sample.controller.response.SampleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

// FIXME: swagger 확인용 샘플 컨트롤러 입니다. 이후 실제 작업 시작할 때 삭제 예정이에요!
@RestController
@RequiredArgsConstructor
@Tag(name = "샘플")
@RequestMapping("/api/sample")
public class SampleController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "swagger 테스트용 API :: 주어진 메세지를 반환한다.")
    public SampleResponse testSwagger(@RequestBody SampleRequest sampleRequest) {
        var message = sampleRequest.message();
        return new SampleResponse(message);
    }
}
