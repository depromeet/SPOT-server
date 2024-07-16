package org.depromeet.spot.ncp.objectstorage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.depromeet.spot.common.exception.media.MediaException.InvalidExtensionException;
import org.depromeet.spot.common.exception.media.MediaException.InvalidReviewMediaException;
import org.depromeet.spot.common.exception.media.MediaException.InvalidStadiumMediaException;
import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.ncp.mock.FakeAmazonS3Config;
import org.depromeet.spot.ncp.mock.FakeTimeUsecase;
import org.depromeet.spot.ncp.property.ObjectStorageProperties;
import org.depromeet.spot.ncp.property.ReviewStorageProperties;
import org.depromeet.spot.ncp.property.StadiumStorageProperties;
import org.depromeet.spot.usecase.port.out.media.CreatePresignedUrlPort.PresignedUrlRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PresignedUrlGeneratorTest {

    private PresignedUrlGenerator presignedUrlGenerator;

    @BeforeEach
    void init() {
        ObjectStorageProperties objectStorageProperties =
                new ObjectStorageProperties("accessKey", "secretKey", "region", "endPoint");
        FakeAmazonS3Config amazonS3 = new FakeAmazonS3Config(objectStorageProperties);

        ReviewStorageProperties reviewStorageProperties =
                new ReviewStorageProperties("review", "folder");
        StadiumStorageProperties stadiumStorageProperties =
                new StadiumStorageProperties("stadium", "folder");

        FakeTimeUsecase fakeTimeUsecase = new FakeTimeUsecase("2024-07-09 21:00:00");
        FileNameGenerator fileNameGenerator =
                FileNameGenerator.builder().timeUsecase(fakeTimeUsecase).build();

        this.presignedUrlGenerator =
                PresignedUrlGenerator.builder()
                        .amazonS3(amazonS3.getAmazonS3())
                        .fileNameGenerator(fileNameGenerator)
                        .reviewStorageProperties(reviewStorageProperties)
                        .stadiumStorageProperties(stadiumStorageProperties)
                        .build();
    }

    @Test
    void 리뷰_속성이_아니라면_리뷰_미디어를_생성할_수_없다() {
        // given
        Long userId = 1L;
        PresignedUrlRequest request = new PresignedUrlRequest("jpg", MediaProperty.STADIUM);

        // when
        // then
        assertThatThrownBy(() -> presignedUrlGenerator.forReview(userId, request))
                .isInstanceOf(InvalidReviewMediaException.class);
    }

    @Test
    void 리뷰_미디어_확장자가_아니라면_리뷰_미디어를_생성할_수_없다() {
        // given
        Long userId = 1L;
        PresignedUrlRequest request = new PresignedUrlRequest("mp4", MediaProperty.REVIEW);

        // when
        // then
        assertThatThrownBy(() -> presignedUrlGenerator.forReview(userId, request))
                .isInstanceOf(InvalidExtensionException.class);
    }

    @Test
    void 경기장_속성이_아니라면_경기장_미디어를_생성할_수_없다() {
        // given
        PresignedUrlRequest request = new PresignedUrlRequest("svg", MediaProperty.REVIEW);

        // when
        // then
        assertThatThrownBy(() -> presignedUrlGenerator.forStadiumSeat(request))
                .isInstanceOf(InvalidStadiumMediaException.class);
    }

    @Test
    void 경기장_미디어_확장자가_아니라면_경기장_미디어를_생성할_수_없다() {
        // given
        PresignedUrlRequest request = new PresignedUrlRequest("mp4", MediaProperty.STADIUM);

        // when
        // then
        assertThatThrownBy(() -> presignedUrlGenerator.forStadiumSeat(request))
                .isInstanceOf(InvalidExtensionException.class);
    }
}
