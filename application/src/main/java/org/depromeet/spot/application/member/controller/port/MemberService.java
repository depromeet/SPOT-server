package org.depromeet.spot.application.member.controller.port;

import org.depromeet.spot.domain.member.Member;

public interface MemberService {

    Member create(String name);
}
