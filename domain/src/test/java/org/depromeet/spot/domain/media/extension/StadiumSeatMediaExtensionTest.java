package org.depromeet.spot.domain.media.extension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.depromeet.spot.common.exception.media.MediaException.InvalidExtensionException;
import org.junit.jupiter.api.Test;

class StadiumSeatMediaExtensionTest {

    @Test
    public void 유효한_좌석_미디어_확장자인지_판별할_수_있다() {
        // given
        final String validValue = "svg";
        final String invalidValue = "png";

        // when
        final boolean checkValid = StadiumSeatMediaExtension.isValid(validValue);
        final boolean checkInvalid = StadiumSeatMediaExtension.isValid(invalidValue);

        // then
        assertTrue(checkValid);
        assertFalse(checkInvalid);
    }

    @Test
    public void value로_좌석_미디어_확장자를_찾을_수_있다() {
        // given
        final String value = "svg";

        // when
        StadiumSeatMediaExtension result = StadiumSeatMediaExtension.from(value);

        // then
        assertEquals(result, StadiumSeatMediaExtension.SVG);
    }

    @Test
    public void 유효하지_않은_value로_좌석_미디어_확장자를_찾을_수_없다() {
        // given
        final String value = "test!";

        // when
        // then
        assertThatThrownBy(() -> StadiumSeatMediaExtension.from(value))
                .isInstanceOf(InvalidExtensionException.class);
    }
}
