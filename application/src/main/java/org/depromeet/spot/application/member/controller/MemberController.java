package org.depromeet.spot.application.member.controller;

import org.depromeet.spot.application.member.dto.request.MemberRequest;
import org.depromeet.spot.application.member.dto.response.MemberResponse;
import org.depromeet.spot.usecase.port.in.MemberUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;

// FIXME: JPA 확인용 샘플 컨트롤러 입니다. 이후 실제 작업 시작할 때 삭제 예정이에요!
@RestController
@RequiredArgsConstructor
@Tag(name = "멤버")
@RequestMapping("/api/members")
public class MemberController {

    private final MemberUsecase memberUsecase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Member 생성 API")
    public MemberResponse create(@RequestBody MemberRequest request) {
        val member = memberUsecase.create(request.name());
        return MemberResponse.from(member);
    }
}
