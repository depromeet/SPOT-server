package org.depromeet.spot.domain.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.depromeet.spot.common.exception.util.UtilException.InvalidHexCodeException;
import org.junit.jupiter.api.Test;

class HexCodeTest {

    @Test
    public void 유효한_값으로_헥사코드를_생성할_수_있다() {
        // given
        final String input = "#FFFFFF";

        // when
        HexCode hexCode = new HexCode(input);

        // then
        assertNotNull(hexCode);
        assertEquals(input, hexCode.getValue());
    }

    @Test
    public void 주어진_값이_null이라면_예외를_반환한다() {
        // given
        // when
        // then
        assertThrows(
                InvalidHexCodeException.class,
                () -> {
                    new HexCode(null);
                });
    }

    @Test
    public void 주어진_값이_6글자가_아니라면_예외를_반환한다() {
        // given
        final String input = "#DFDFDFDF";

        // when
        // then
        assertThrows(
                InvalidHexCodeException.class,
                () -> {
                    new HexCode(input);
                });
    }

    @Test
    public void 주어진_값이_헥사코드_범위가_아니라면_예외를_반환한다() {
        // given
        final String input = "123456";

        // when
        // then
        assertThrows(
                InvalidHexCodeException.class,
                () -> {
                    new HexCode(input);
                });
    }
}
