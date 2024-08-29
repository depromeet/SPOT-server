package org.depromeet.spot.application.member.controller;

import jakarta.validation.Valid;

import org.depromeet.spot.application.common.jwt.JwtTokenUtil;
import org.depromeet.spot.application.member.dto.request.RegisterV2Req;
import org.depromeet.spot.application.member.dto.response.JwtTokenResponse;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.depromeet.spot.usecase.port.in.oauth.OauthUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
@Tag(name = "(v2) Oauth")
public class OauthController {

    private final OauthUsecase oauthUsecase;

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/api/v2/members")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Member 회원가입 API")
    public JwtTokenResponse create(@RequestBody @Valid RegisterV2Req request) {

        Member member = request.toDomain();
        Member memberResult = oauthUsecase.create(request.accessToken(), member);

        return new JwtTokenResponse(jwtTokenUtil.getJWTToken(memberResult));
    }

    @GetMapping("/api/v2/members/{snsProvider}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Member 로그인 API")
    public JwtTokenResponse login(
            @PathVariable("snsProvider")
                    @Parameter(name = "snsProvider", description = "KAKAO/GOOGLE", required = true)
                    SnsProvider snsProvider,
            @RequestParam
                    @Parameter(
                            name = "accessToken",
                            description = "sns 카카오는 accessToken",
                            required = true)
                    String accessToken) {

        Member member = oauthUsecase.login(snsProvider, accessToken);
        return new JwtTokenResponse(jwtTokenUtil.getJWTToken(member));
    }

    //    TODO : /api/v2/members를 RequestMapping으로 빼면 구글 로그인에서 4xx Exception 발생
    @GetMapping("/api/v2/members/authorization/{snsProvider}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "accessToken을 받아오기 위한 API")
    public String getAccessToken(
            @PathVariable("snsProvider") SnsProvider snsProvider, @RequestParam String code) {
        String token = oauthUsecase.getOauthAccessToken(snsProvider, code);
        log.info("snsProvider : {}", snsProvider);
        log.info("token : \n{}", token);
        return token;
    }
}
