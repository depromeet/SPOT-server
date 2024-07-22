package org.depromeet.spot.application.member.controller;

import org.depromeet.spot.application.common.annotation.CurrentMember;
import org.depromeet.spot.application.common.jwt.JwtTokenUtil;
import org.depromeet.spot.application.member.dto.response.MyHomeResponse;
import org.depromeet.spot.usecase.port.in.member.MyHomeUsecase;
import org.depromeet.spot.usecase.port.in.member.MyHomeUsecase.MyHome;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
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

    @CurrentMember
    @GetMapping("/myHome")
    @ResponseStatus(HttpStatus.OK)
    public MyHomeResponse findMyHomeInfo(@Parameter(hidden = true) Long memberId) {
        MyHome myHome = myHomeUsecase.findMyHomeInfo(memberId);

        return MyHomeResponse.from(myHome);
    }
}
