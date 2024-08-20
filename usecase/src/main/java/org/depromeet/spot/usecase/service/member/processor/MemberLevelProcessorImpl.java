package org.depromeet.spot.usecase.service.member.processor;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.usecase.port.in.member.UpdateMemberUsecase;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberLevelProcessorImpl implements MemberLevelProcessor {
    private final ReadReviewUsecase readReviewUsecase;
    private final UpdateMemberUsecase updateMemberUsecase;

    @Override
    public Member calculateAndUpdateMemberLevel(Member member) {
        final long memberReviewCnt = readReviewUsecase.countByMember(member.getId());
        return updateMemberUsecase.updateLevel(member, memberReviewCnt);
    }
}
