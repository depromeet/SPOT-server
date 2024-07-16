package org.depromeet.spot.ncp.objectstorage;

import java.net.URL;
import java.util.Date;

import org.depromeet.spot.common.exception.media.MediaException.InvalidExtensionException;
import org.depromeet.spot.common.exception.media.MediaException.InvalidReviewMediaException;
import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.domain.media.extension.ImageExtension;
import org.depromeet.spot.ncp.property.ReviewStorageProperties;
import org.depromeet.spot.usecase.port.out.media.CreatePresignedUrlPort;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class PresignedUrlGenerator implements CreatePresignedUrlPort {

    private final AmazonS3 amazonS3;
    private final FileNameGenerator fileNameGenerator;
    private final ReviewStorageProperties reviewStorageProperties;

    private static final long EXPIRE_MS = 1000 * 60 * 5L;

    @Override
    public String forReview(final Long userId, PresignedUrlRequest request) {
        isValidReviewMedia(request.getProperty(), request.getFileExtension());

        final ImageExtension fileExtension = ImageExtension.from(request.getFileExtension());
        final String folderName = reviewStorageProperties.folderName();
        final String fileName =
                fileNameGenerator.createReviewFileName(userId, fileExtension, folderName);
        final URL url = createPresignedUrl(reviewStorageProperties.bucketName(), fileName);

        return url.toString();
    }

    // 1차 MVP에서 사진만 허용
    private void isValidReviewMedia(final MediaProperty property, final String fileExtension) {
        if (property != MediaProperty.REVIEW) {
            throw new InvalidReviewMediaException();
        }

        if (!ImageExtension.isValid(fileExtension)) {
            throw new InvalidExtensionException(fileExtension);
        }
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
