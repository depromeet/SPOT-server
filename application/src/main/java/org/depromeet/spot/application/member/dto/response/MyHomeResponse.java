package org.depromeet.spot.application.member.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.usecase.port.in.member.MyHomeUsecase.MyHome;

import lombok.Builder;

@Builder
public record MyHomeResponse(
        String profileImageUrl, // 멤버
        String nickname, // 멤버
        Integer level, // 멤버
        Long totalReviewCount, // 리뷰
        String teamImageUrl, // 팀
        String stadiumName, // 리뷰
        LocalDateTime date, // 리뷰
        List<ImageResponse> reviewImages, // 리뷰
        String blockName, // 리뷰
        Long seatNumber // 리뷰
        ) {
    public static MyHomeResponse from(MyHome myHome) {
        return MyHomeResponse.builder()
                .profileImageUrl(myHome.getMember().getProfileImage())
                .nickname(myHome.getMember().getNickname())
                .level(myHome.getMember().getLevel())
                .teamImageUrl(myHome.getBaseballTeam().getLogo())
                .build();
    }

    record ImageResponse(Long id, String url) {}
}
