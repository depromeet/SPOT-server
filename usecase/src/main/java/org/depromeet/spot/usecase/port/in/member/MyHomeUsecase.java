package org.depromeet.spot.usecase.port.in.member;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.team.BaseballTeam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface MyHomeUsecase {

    MyHome findMyHomeInfo(Long memberId);

    @Builder
    @Getter
    @AllArgsConstructor
    class MyHome {
        Member member;
        BaseballTeam baseballTeam;
        Review review;
    }
}
