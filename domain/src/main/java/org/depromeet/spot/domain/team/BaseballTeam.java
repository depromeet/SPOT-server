package org.depromeet.spot.domain.team;

import org.depromeet.spot.common.exception.team.TeamException.InvalidBaseballAliasNameException;
import org.depromeet.spot.common.exception.team.TeamException.InvalidBaseballTeamNameException;
import org.depromeet.spot.domain.common.RgbCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseballTeam {

    private final Long id;
    private final String name;
    private final String alias;
    private final String logo;
    private final RgbCode labelRgbCode;

    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_ALIAS_LENGTH = 10;

    // TODO: @Builder을 썼을 때 이 생성자를 사용하는지 테스트 필요함
    public BaseballTeam(Long id, String name, String alias, String logo, RgbCode labelRgbCode) {
        isValidName(name);
        isValidAlias(alias);

        this.id = id;
        this.name = name;
        this.alias = alias;
        this.logo = logo;
        this.labelRgbCode = labelRgbCode;
    }

    private void isValidName(final String name) {
        if (name == null || name.length() > MAX_NAME_LENGTH) {
            throw new InvalidBaseballTeamNameException();
        }
    }

    private void isValidAlias(final String alias) {
        if (alias == null) return;
        if (alias.length() > MAX_ALIAS_LENGTH) {
            throw new InvalidBaseballAliasNameException();
        }
    }
}
