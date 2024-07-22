package org.depromeet.spot.application.member.controller;

import jakarta.validation.Valid;

import org.depromeet.spot.application.common.jwt.JwtTokenUtil;
import org.depromeet.spot.application.member.dto.request.RegisterReq;
import org.depromeet.spot.application.member.dto.response.JwtTokenResponse;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.usecase.port.in.member.MemberUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "멤버")
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberUsecase memberUsecase;

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Member 회원가입 API")
    public JwtTokenResponse create(@RequestBody @Valid RegisterReq request) {

        Member member = request.toDomain();
        Member memberResult = memberUsecase.create(request.accessToken(), member);

        return new JwtTokenResponse(jwtTokenUtil.getJWTToken(memberResult));
    }

    @GetMapping("/{accessToken}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Member 로그인 API")
    public JwtTokenResponse login(
            @PathVariable("accessToken")
                    @Parameter(
                            name = "accessToken",
                            description = "sns accessToken",
                            required = true)
                    String accessToken) {

        Member member = memberUsecase.login(accessToken);
        if (member == null) {
            return new JwtTokenResponse("");
        }
        return new JwtTokenResponse(jwtTokenUtil.getJWTToken(member));
    }

    @GetMapping("/duplicatedNickname/{nickname}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "닉네임 중복확인 API")
    public Boolean duplicatedNickname(
            @PathVariable("nickname")
                    @Parameter(name = "nickname", description = "닉네임", required = true)
                    String nickname) {
        Boolean result = memberUsecase.duplicatedNickname(nickname);
        return result;
    }

    @GetMapping("/accessToken/{idCode}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "(백엔드용)accessToken을 받아오기 위한 API")
    public String getAccessToken(
            @PathVariable("idCode")
                    @Parameter(name = "idCode", description = "카카오에서 발급 받은 idCode", required = true)
                    String idCode) {
        return memberUsecase.getAccessToken(idCode);
    }

    @DeleteMapping("/{accessToken}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "(개발용) Member 삭제 API")
    public Boolean deleteMember(
            @PathVariable("accessToken")
                    @Parameter(
                            name = "accessToken",
                            description = "sns accessToken",
                            required = true)
                    String accessToken) {
        // TODO : (개발용) 유저 탈퇴 아님! 단순 유저 삭제만 진행함.
        return memberUsecase.deleteMember(accessToken);
    }
}
