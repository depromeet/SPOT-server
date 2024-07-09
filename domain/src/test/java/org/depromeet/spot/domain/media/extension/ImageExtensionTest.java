package org.depromeet.spot.domain.media.extension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.depromeet.spot.common.exception.media.MediaException.InvalidExtensionException;
import org.junit.jupiter.api.Test;

class ImageExtensionTest {

    @Test
    public void 유효한_리뷰_미디어_확장자인지_판별할_수_있다() {
        // given
        final String validValue = "jpg";
        final String invalidValue = "mp4";

        // when
        final boolean checkValid = ImageExtension.isValid(validValue);
        final boolean checkInvalid = ImageExtension.isValid(invalidValue);

        // then
        assertTrue(checkValid);
        assertFalse(checkInvalid);
    }

    @Test
    public void value로_리뷰_미디어_확장자를_찾을_수_있다() {
        // given
        final String jpgValue = "jpg";
        final String jpegValue = "jpeg";
        final String pngValue = "png";

        // when
        ImageExtension jpgResult = ImageExtension.from(jpgValue);
        ImageExtension jpegResult = ImageExtension.from(jpegValue);
        ImageExtension pngResult = ImageExtension.from(pngValue);

        // then
        assertAll(
                () -> assertEquals(jpgResult, ImageExtension.JPG),
                () -> assertEquals(jpegResult, ImageExtension.JPEG),
                () -> assertEquals(pngResult, ImageExtension.PNG));
    }

    @Test
    public void 유효하지_않은_value로_좌석_미디어_확장자를_찾을_수_없다() {
        // given
        final String value = "test!";

        // when
        // then
        assertThatThrownBy(() -> ImageExtension.from(value))
                .isInstanceOf(InvalidExtensionException.class);
    }
}
