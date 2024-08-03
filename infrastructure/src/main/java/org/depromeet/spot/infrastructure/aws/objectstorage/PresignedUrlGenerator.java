package org.depromeet.spot.infrastructure.aws.objectstorage;

import java.net.URL;
import java.util.Date;

import org.depromeet.spot.common.exception.media.MediaException.InvalidExtensionException;
import org.depromeet.spot.domain.media.extension.ImageExtension;
import org.depromeet.spot.infrastructure.aws.config.ObjectStorageConfig;
import org.depromeet.spot.usecase.port.out.media.CreatePresignedUrlPort;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class PresignedUrlGenerator implements CreatePresignedUrlPort {

    private final AmazonS3 amazonS3;
    private final FileNameGenerator fileNameGenerator;

    private static final long EXPIRE_MS = 1000 * 60 * 5L;

    @Override
    public String forImage(final Long memberId, PresignedUrlRequest request) {
        isValidImageExtension(request.getFileExtension());

        final ImageExtension fileExtension = ImageExtension.from(request.getFileExtension());
        final String folderName = request.getProperty().getFolderName();
        final String fileName =
                fileNameGenerator.createFileName(memberId, fileExtension, folderName);
        final URL url = createPresignedUrl(fileName);

        return url.toString();
    }

    private void isValidImageExtension(final String fileExtension) {
        if (!ImageExtension.isValid(fileExtension)) {
            throw new InvalidExtensionException(fileExtension);
        }
    }

    private URL createPresignedUrl(final String fileName) {
        return amazonS3.generatePresignedUrl(createGeneratePreSignedUrlRequest(fileName));
    }

    private GeneratePresignedUrlRequest createGeneratePreSignedUrlRequest(final String fileName) {
        final String bucketName = ObjectStorageConfig.BUCKET_NAME;
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
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
