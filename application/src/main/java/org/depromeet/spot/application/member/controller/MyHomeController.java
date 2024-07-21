package org.depromeet.spot.application.member.controller;

import org.depromeet.spot.application.common.jwt.JwtTokenUtil;
import org.depromeet.spot.application.member.dto.response.MyHomeResponse;
import org.depromeet.spot.usecase.port.in.member.MyHomeUsecase;
import org.depromeet.spot.usecase.port.in.member.MyHomeUsecase.MyHome;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "홈 피드")
@RequestMapping("/api/v1/")
public class MyHomeController {

    private final MyHomeUsecase myHomeUsecase;

    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/myHome")
    @ResponseStatus(HttpStatus.OK)
    public MyHomeResponse findMyHomeInfo(@RequestHeader("Authorization") String jwtToken) {
        // 접두사 제거
        if (jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
        }
        // TODO : 토큰 내의 memberId Long 타입으로 변경
        // TODO : 토큰에서 memberId 가져오는 AOP 추가
        String memberIdStr = jwtTokenUtil.getIdFromJWT(jwtToken);
        Long memberId = Long.parseLong(memberIdStr);
        MyHome myHome = myHomeUsecase.findMyHomeInfo(memberId);

        return MyHomeResponse.from(myHome);
    }
}
