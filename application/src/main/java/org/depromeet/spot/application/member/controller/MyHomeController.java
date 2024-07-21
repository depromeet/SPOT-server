package org.depromeet.spot.application.member.controller;

import org.depromeet.spot.application.member.dto.response.MyHomeResponse;
import org.depromeet.spot.domain.member.MyHome;
import org.depromeet.spot.usecase.port.in.member.MyHomeUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/myHome")
    @ResponseStatus(HttpStatus.OK)
    public MyHomeResponse findMyHomeInfo() {
        MyHome myHome = myHomeUsecase.findMyHomeInfo();

        return new MyHomeResponse();
    }
}
