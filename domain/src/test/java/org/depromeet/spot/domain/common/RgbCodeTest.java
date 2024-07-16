package org.depromeet.spot.domain.common;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.stream.Stream;

import org.depromeet.spot.common.exception.util.UtilException.InvalidRgbCodeException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RgbCodeTest {

    @ParameterizedTest
    @MethodSource("provideRgbValues")
    void rgb_범위_외의_값으로_RgbCode를_생성할_수_없다(Integer red, Integer green, Integer blue) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new RgbCode(red, green, blue))
                .isInstanceOf(InvalidRgbCodeException.class);
    }

    private static Stream<Arguments> provideRgbValues() {
        return Stream.of(
                Arguments.of(null, 255, 255),
                Arguments.of(255, null, 255),
                Arguments.of(255, 255, null));
    }
}
