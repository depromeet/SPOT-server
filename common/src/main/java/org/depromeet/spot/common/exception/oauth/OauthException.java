package org.depromeet.spot.common.exception.oauth;

import org.depromeet.spot.common.exception.BusinessException;
import org.depromeet.spot.common.exception.ErrorCode;

public abstract class OauthException extends BusinessException {

    public OauthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class InvalidAcessTokenException extends OauthException {
        public InvalidAcessTokenException() {
            super(OauthErrorCode.INVALID_ACCESS_TOKEN);
        }
    }

    public static class InternalOauthServerException extends OauthException {
        public InternalOauthServerException() {
            super(OauthErrorCode.INTERNAL_OAUTH_SERVER_ERROR);
        }
    }
}
