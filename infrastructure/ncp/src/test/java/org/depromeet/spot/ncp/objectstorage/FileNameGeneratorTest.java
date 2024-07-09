package org.depromeet.spot.ncp.objectstorage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.depromeet.spot.domain.media.extension.ImageExtension;
import org.depromeet.spot.domain.media.extension.StadiumSeatMediaExtension;
import org.depromeet.spot.ncp.mock.FakeTimeUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileNameGeneratorTest {

    private FileNameGenerator fileNameGenerator;

    @BeforeEach
    void init() {
        FakeTimeUsecase fakeTimeUsecase = new FakeTimeUsecase("2024-07-09 21:00:00");
        this.fileNameGenerator = FileNameGenerator.builder().timeUsecase(fakeTimeUsecase).build();
    }

    @Test
    void 리뷰_첨부파일_이름을_생성할_수_있다() {
        // given
        Long userId = 1L;
        ImageExtension extension = ImageExtension.JPG;

        // when
        final String fileName = fileNameGenerator.createReviewFileName(userId, extension);

        // then
        assertThat(fileName).isEqualTo("REVIEW/user/1/2024-07-09T21:00/jpg");
    }

    @Test
    void 경기장_첨부파일_이름을_생성할_수_있다() {
        // given
        StadiumSeatMediaExtension extension = StadiumSeatMediaExtension.SVG;

        // when
        final String fileName = fileNameGenerator.createStadiumFileName(extension);

        // then
        assertThat(fileName).isEqualTo("STADIUM/2024-07-09T21:00/svg");
    }
}
