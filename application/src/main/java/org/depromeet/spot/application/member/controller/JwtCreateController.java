package org.depromeet.spot.application.member.controller;

import org.depromeet.spot.application.common.jwt.JwtTokenUtil;
import org.depromeet.spot.application.member.dto.response.JwtTokenResponse;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.MemberRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Jwt 생성용")
@RequestMapping("/api/v1/jwts")
public class JwtCreateController {

    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/{id}")
    public JwtTokenResponse jwtTokenCreate(
            @PathVariable("id")
                    @Parameter(name = "id", description = "생성하고자하는 jwt 토큰의 id", required = true)
                    Long id) {
        Member member = Member.builder().id(id).role(MemberRole.ROLE_USER).build();

        return new JwtTokenResponse(jwtTokenUtil.getJWTToken(member));
    }
}
