package org.depromeet.spot.application.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.depromeet.spot.application.common.jwt.JwtTokenUtil;
import org.depromeet.spot.application.member.dto.request.RegisterReq;
import org.depromeet.spot.application.member.dto.response.MemberResponse;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.usecase.port.in.member.MemberUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "멤버")
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberUsecase memberUsecase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Member 생성 API")
    public MemberResponse create(@RequestBody MemberRequest request) {
        val member = memberUsecase.create(request.name());
        return MemberResponse.from(member);
    }

    @GetMapping("/duplicatedNickname")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "닉네임 중복확인 API")
    public Boolean duplicatedNickname(
        @RequestParam("nickname")
        @Parameter(name = "nickname", description = "닉네임", required = true)
        String nickname) {
        Boolean result = memberUsecase.duplicatedNickname(nickname);
        return result;
    }
}
