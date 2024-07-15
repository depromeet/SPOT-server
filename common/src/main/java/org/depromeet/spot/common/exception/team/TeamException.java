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

    public static class InvalidBaseballTeamNameException extends TeamException {
        public InvalidBaseballTeamNameException() {
            super(TeamErrorCode.INVALID_TEAM_NAME_NOT_FOUND);
        }
    }

    public static class InvalidBaseballAliasNameException extends TeamException {
        public InvalidBaseballAliasNameException() {
            super(TeamErrorCode.INVALID_TEAM_ALIAS_NOT_FOUND);
        }
    }

    public static class DuplicateTeamNameException extends TeamException {
        public DuplicateTeamNameException() {
            super(TeamErrorCode.DUPLICATE_TEAM_NAME);
        }
    }

    public static class EmptyTeamLogoException extends TeamException {
        public EmptyTeamLogoException() {
            super(TeamErrorCode.EMPTY_TEAM_LOGO);
        }
    }
}
