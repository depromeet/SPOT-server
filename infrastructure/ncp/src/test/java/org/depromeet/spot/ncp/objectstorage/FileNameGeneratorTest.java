package org.depromeet.spot.ncp.objectstorage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.depromeet.spot.domain.media.extension.ImageExtension;
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
    void 첨부_파일_이름을_생성할_수_있다() {
        // given
        Long userId = 1L;
        ImageExtension extension = ImageExtension.JPG;

        // when
        final String folderName = "folder-names";
        final String fileName = fileNameGenerator.createFileName(userId, extension, folderName);

        // then
        assertThat(fileName).isEqualTo("folder-names/user_1_2024-07-09T21:00.jpg");
    }
}
