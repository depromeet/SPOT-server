package org.depromeet.spot.common.exception.team;

import org.depromeet.spot.common.exception.BusinessException;

public abstract class TeamException extends BusinessException {

    protected TeamException(TeamErrorCode errorCode) {
        super(errorCode);
    }

    public static class BaseballTeamNotFoundException extends TeamException {
        public BaseballTeamNotFoundException() {
            super(TeamErrorCode.BASEBALL_TEAM_NOT_FOUND);
        }
    }
}
