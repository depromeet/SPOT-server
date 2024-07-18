package org.depromeet.spot.application.member.dto.request;

import org.depromeet.spot.usecase.port.in.member.UpdateMemberUsecase.UpdateProfileCommand;

public record UpdateProfileRequest(String profileImage, String nickname, Long teamId) {

    public UpdateProfileCommand toCommand() {
        return UpdateProfileCommand.builder()
                .nickname(nickname)
                .profileImage(profileImage)
                .teamId(teamId)
                .build();
    }
}
