package org.depromeet.spot.application.member.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.member.dto.request.UpdateProfileRequest;
import org.depromeet.spot.application.member.dto.response.MemberProfileResponse;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.usecase.port.in.member.UpdateMemberUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "멤버")
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class UpdateMemberController {

    private final UpdateMemberUsecase updateMemberUsecase;

    @PutMapping("/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Member 프로필 수정 API")
    public MemberProfileResponse updateProfile(
            @PathVariable @NotNull @Positive final Long memberId,
            @RequestBody @Valid @NotNull UpdateProfileRequest request) {
        Member member = updateMemberUsecase.updateProfile(memberId, request.toCommand());
        return MemberProfileResponse.from(member);
    }
}
