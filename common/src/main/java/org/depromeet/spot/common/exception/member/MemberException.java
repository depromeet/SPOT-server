package org.depromeet.spot.common.exception.member;

import org.depromeet.spot.common.exception.BusinessException;

public abstract class MemberException extends BusinessException {

    protected MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }

    public static class MemberNotFoundException extends MemberException {
        public MemberNotFoundException() {
            super(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        public MemberNotFoundException(Object o) {
            super(MemberErrorCode.MEMBER_NOT_FOUND.appended(o));
        }
    }
}
