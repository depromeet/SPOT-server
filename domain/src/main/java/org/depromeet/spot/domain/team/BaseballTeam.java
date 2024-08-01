package org.depromeet.spot.domain.team;

import org.depromeet.spot.common.exception.team.TeamException.EmptyTeamLogoException;
import org.depromeet.spot.common.exception.team.TeamException.InvalidBaseballAliasNameException;
import org.depromeet.spot.common.exception.team.TeamException.InvalidBaseballTeamNameException;
import org.depromeet.spot.domain.common.HexCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseballTeam {

    private final Long id;
    private final String name;
    private final String alias;
    private final String logo;
    private final HexCode labelBackgroundColor;
    private final HexCode labelFontColor;

    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_ALIAS_LENGTH = 10;

    public BaseballTeam(
            Long id,
            String name,
            String alias,
            String logo,
            HexCode labelBackgroundColor,
            HexCode labelFontColor) {
        checkValidName(name);
        checkValidAlias(alias);
        checkValidLogo(logo);

        this.id = id;
        this.name = name;
        this.alias = alias;
        this.logo = logo;
        this.labelBackgroundColor = labelBackgroundColor;
        this.labelFontColor = labelFontColor;
    }

    private void checkValidName(final String name) {
        if (isNullOrBlank(name) || name.length() > MAX_NAME_LENGTH) {
            throw new InvalidBaseballTeamNameException();
        }
    }

    private void checkValidAlias(final String alias) {
        if (isNullOrBlank(alias) || alias.length() > MAX_ALIAS_LENGTH) {
            throw new InvalidBaseballAliasNameException();
        }
    }

    private void checkValidLogo(final String logo) {
        if (isNullOrBlank(logo)) {
            throw new EmptyTeamLogoException();
        }
    }

    private boolean isNullOrBlank(String str) {
        return str == null || str.isBlank();
    }
}
