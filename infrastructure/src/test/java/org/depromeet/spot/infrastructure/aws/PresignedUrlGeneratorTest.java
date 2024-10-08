package org.depromeet.spot.infrastructure.aws;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.depromeet.spot.common.exception.media.MediaException.InvalidExtensionException;
import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.infrastructure.aws.objectstorage.FileNameGenerator;
import org.depromeet.spot.infrastructure.aws.objectstorage.PresignedUrlGenerator;
import org.depromeet.spot.infrastructure.aws.property.ObjectStorageProperties;
import org.depromeet.spot.infrastructure.mock.FakeAmazonS3Config;
import org.depromeet.spot.infrastructure.mock.FakeTimeUsecase;
import org.depromeet.spot.usecase.port.out.media.CreatePresignedUrlPort.PresignedUrlRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PresignedUrlGeneratorTest {

    private PresignedUrlGenerator presignedUrlGenerator;

    @BeforeEach
    void init() {
        ObjectStorageProperties objectStorageProperties =
                new ObjectStorageProperties(
                        "accessKey", "secretKey", "bucketName", "basicProfileImageUrl");
        FakeAmazonS3Config amazonS3 = new FakeAmazonS3Config(objectStorageProperties);

        FakeTimeUsecase fakeTimeUsecase = new FakeTimeUsecase("2024-07-09 21:00:00");
        FileNameGenerator fileNameGenerator =
                FileNameGenerator.builder().timeUsecase(fakeTimeUsecase).build();

        this.presignedUrlGenerator =
                PresignedUrlGenerator.builder()
                        .amazonS3(amazonS3.getAmazonS3())
                        .fileNameGenerator(fileNameGenerator)
                        .build();
    }

    @Test
    void 이미지_확장자가_아니라면_이미지_미디어를_생성할_수_없다() {
        // given
        Long userId = 1L;
        MediaProperty property = MediaProperty.PROFILE_IMAGE;
        PresignedUrlRequest request = new PresignedUrlRequest("mp4", property);

        // when
        // then
        assertThatThrownBy(() -> presignedUrlGenerator.forImage(userId, request))
                .isInstanceOf(InvalidExtensionException.class);
    }
}
