package org.depromeet.spot.domain.media;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.depromeet.spot.common.exception.media.MediaException.InvalidMediaException;
import org.junit.jupiter.api.Test;

class MediaTest {

    @Test
    public void url이_없으면_미디어를_생성할_수_없다() {
        // given
        final String url = "";
        final String fileName = "file";

        // when
        // then
        assertThatThrownBy(() -> new Media(url, fileName))
                .isInstanceOf(InvalidMediaException.class);
    }

    @Test
    public void fileName이_없으면_미디어를_생성할_수_없다() {
        // given
        final String url = "url";
        final String fileName = "";

        // when
        // then
        assertThatThrownBy(() -> new Media(url, fileName))
                .isInstanceOf(InvalidMediaException.class);
    }
}
