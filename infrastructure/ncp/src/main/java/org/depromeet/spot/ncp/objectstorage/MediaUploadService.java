package org.depromeet.spot.ncp.objectstorage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;

import org.depromeet.spot.domain.media.Media;
import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.domain.media.ReviewMediaExtension;
import org.depromeet.spot.domain.media.StadiumMediaExtension;
import org.depromeet.spot.ncp.property.ReviewStorageProperties;
import org.depromeet.spot.ncp.property.StadiumStorageProperties;
import org.depromeet.spot.usecase.port.out.media.MediaUploadPort;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaUploadService implements MediaUploadPort {

    private final AmazonS3 amazonS3;
    private final ReviewStorageProperties reviewStorageProperties;
    private final StadiumStorageProperties stadiumStorageProperties;

    private static final long EXPIRE_MS = 1000 * 60 * 2;

    @Override
    public Media forReview(final Long userId, PresignedUrlRequest request) {
        isValidReviewMedia(request.getProperty(), request.getFileExtension());

        final ReviewMediaExtension fileExtension =
                ReviewMediaExtension.from(request.getFileExtension());
        final String fileName = createReviewFileName(userId, fileExtension);
        final URL url = createPresignedUrl(reviewStorageProperties.bucketName(), fileName);

        return new Media(url.toString(), fileName);
    }

    private void isValidReviewMedia(final MediaProperty property, final String fileExtension) {
        if (property != MediaProperty.REVIEW) {
            throw new IllegalArgumentException("리뷰와 관련된 미디어가 아닙니다.");
        }

        if (!ReviewMediaExtension.isValidReviewMedia(fileExtension)) {
            throw new IllegalArgumentException("리뷰 첨부파일은 사진 혹은 비디오만 가능합니다.");
        }
    }

    private String createReviewFileName(
            final Long userId, final ReviewMediaExtension fileExtension) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(MediaProperty.REVIEW)
                .append("/user/")
                .append(userId)
                .append("/")
                .append(LocalDateTime.now())
                .append("/")
                .append(fileExtension.getValue());
        return stringBuilder.toString();
    }

    @Override
    public Media forStadium(PresignedUrlRequest request) {
        isValidStadiumMedia(request.getProperty(), request.getFileExtension());

        final StadiumMediaExtension fileExtension =
                StadiumMediaExtension.from(request.getFileExtension());
        final String fileName = createStadiumFileName(fileExtension);
        final URL url = createPresignedUrl(stadiumStorageProperties.bucketName(), fileName);

        return new Media(url.toString(), fileName);
    }

    private void isValidStadiumMedia(final MediaProperty property, final String fileExtension) {
        if (property != MediaProperty.STADIUM) {
            throw new IllegalArgumentException("경기장과 관련된 미디어가 아닙니다.");
        }

        if (!ReviewMediaExtension.isValidReviewMedia(fileExtension)) {
            throw new IllegalArgumentException("경기장 좌석배치도는 사진만 가능합니다.");
        }
    }

    private String createStadiumFileName(final StadiumMediaExtension fileExtension) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(MediaProperty.STADIUM)
                .append("/")
                .append(LocalDateTime.now())
                .append("/")
                .append(fileExtension.getValue());
        return stringBuilder.toString();
    }

    private URL createPresignedUrl(final String bucketName, final String fileName) {
        return amazonS3.generatePresignedUrl(
                createGeneratePreSignedUrlRequest(bucketName, fileName));
    }

    private GeneratePresignedUrlRequest createGeneratePreSignedUrlRequest(
            final String bucket, final String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withKey(fileName)
                        .withExpiration(createPreSignedUrlExpiration());

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());

        return generatePresignedUrlRequest;
    }

    private Date createPreSignedUrlExpiration() {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + EXPIRE_MS);
        return expiration;
    }
}
