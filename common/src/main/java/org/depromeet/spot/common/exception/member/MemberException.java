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

    public static class MemberNicknameConflictException extends MemberException {
        public MemberNicknameConflictException() {
            super(MemberErrorCode.MEMBER_NICKNAME_CONFLICT);
        }
    }

    public static class InvalidLevelException extends MemberException {
        public InvalidLevelException() {
            super(MemberErrorCode.INVALID_LEVEL);
        }
    }

    public static class InactiveMemberException extends MemberException {

        public InactiveMemberException() {
            super(MemberErrorCode.INACTIVE_MEMBER);
        }
    }

    public static class MemberConflictException extends MemberException {

        public MemberConflictException() {
            super(MemberErrorCode.MEMBER_CONFLICT);
        }
    }
}
