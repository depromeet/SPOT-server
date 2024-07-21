package org.depromeet.spot.application.member.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record MyHomeResponse(
        String profileImageUrl, // 멤버
        String nickname, // 멤버
        Long level, // 멤버
        Long totalReviewCount, // 리뷰
        String teamImageUrl, // 팀
        String stadiumName, // 리뷰
        LocalDateTime date, // 리뷰
        List<ImageResponse> reviewImages, // 리뷰
        String blockName, // 리뷰
        Long seatNumber // 리뷰
        ) {
    record ImageResponse(Long id, String url) {}
}
