package org.depromeet.spot.usecase.service.member.processor;

import org.depromeet.spot.domain.member.Member;

public interface MemberLevelProcessor {
    Member calculateAndUpdateMemberLevel(Member member);
}
