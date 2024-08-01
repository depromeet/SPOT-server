package org.depromeet.spot.domain.team;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.depromeet.spot.common.exception.team.TeamException.EmptyTeamLogoException;
import org.depromeet.spot.common.exception.team.TeamException.InvalidBaseballAliasNameException;
import org.depromeet.spot.common.exception.team.TeamException.InvalidBaseballTeamNameException;
import org.depromeet.spot.domain.common.HexCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class BaseballTeamTest {

    @ParameterizedTest
    @NullAndEmptySource
    void name이_null_또는_blank일_때_BaseballTeam을_생성할_수_없다(String name) {
        // given
        HexCode backgroundColor = new HexCode("#FFFFFF");

        // when
        // then
        assertAll(
                () ->
                        assertThatThrownBy(
                                        () ->
                                                new BaseballTeam(
                                                        1L, name, "alias", "logo", backgroundColor))
                                .isInstanceOf(InvalidBaseballTeamNameException.class),
                () ->
                        assertThatThrownBy(
                                        () ->
                                                BaseballTeam.builder()
                                                        .name(name)
                                                        .alias("alias")
                                                        .logo("logo")
                                                        .build())
                                .isInstanceOf(InvalidBaseballTeamNameException.class));
    }

    @Test
    void name_길이는_20글자를_초과할_수_없다() {
        // given
        HexCode backgroundColor = new HexCode("#FFFFFF");
        final String name = "012345678901234567890";

        // when
        // then
        assertThatThrownBy(() -> new BaseballTeam(1L, name, "alias", "logo", backgroundColor))
                .isInstanceOf(InvalidBaseballTeamNameException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void alias가_null_또는_blank일_때_BaseballTeam을_생성할_수_없다(String alias) {
        // given
        HexCode backgroundColor = new HexCode("#FFFFFF");

        // when
        // then
        assertAll(
                () ->
                        assertThatThrownBy(
                                        () ->
                                                new BaseballTeam(
                                                        1L, "name", alias, "logo", backgroundColor))
                                .isInstanceOf(InvalidBaseballAliasNameException.class),
                () ->
                        assertThatThrownBy(
                                        () ->
                                                BaseballTeam.builder()
                                                        .name("name")
                                                        .alias(alias)
                                                        .logo("logo")
                                                        .build())
                                .isInstanceOf(InvalidBaseballAliasNameException.class));
    }

    @Test
    void alias_길이는_10글자를_초과할_수_없다() {
        // given
        HexCode backgroundColor = new HexCode("#FFFFFF");
        final String alias = "01234567890";

        // when
        // then
        assertThatThrownBy(() -> new BaseballTeam(1L, "name", alias, "logo", backgroundColor))
                .isInstanceOf(InvalidBaseballAliasNameException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void logo가_null_또는_blank일_때_BaseballTeam을_생성할_수_없다(String logo) {
        // given
        HexCode backgroundColor = new HexCode("#FFFFFF");

        // when
        // then
        assertAll(
                () ->
                        assertThatThrownBy(
                                        () ->
                                                new BaseballTeam(
                                                        1L, "name", "alias", logo, backgroundColor))
                                .isInstanceOf(EmptyTeamLogoException.class),
                () ->
                        assertThatThrownBy(
                                        () ->
                                                BaseballTeam.builder()
                                                        .name("name")
                                                        .alias("alias")
                                                        .logo(logo)
                                                        .build())
                                .isInstanceOf(EmptyTeamLogoException.class));
    }
}
