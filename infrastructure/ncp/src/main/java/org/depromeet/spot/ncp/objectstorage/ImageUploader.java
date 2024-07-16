package org.depromeet.spot.ncp.objectstorage;

import java.io.IOException;
import java.io.InputStream;

import org.depromeet.spot.common.exception.media.MediaException.InvalidExtensionException;
import org.depromeet.spot.common.exception.media.MediaException.UploadFailException;
import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.domain.media.extension.ImageExtension;
import org.depromeet.spot.domain.media.extension.StadiumSeatMediaExtension;
import org.depromeet.spot.ncp.config.ObjectStorageConfig;
import org.depromeet.spot.usecase.port.out.media.ImageUploadPort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageUploader implements ImageUploadPort {

    private final AmazonS3 amazonS3;

    @Override
    public String upload(String targetName, MultipartFile file, MediaProperty property) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        final String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        checkValidExtension(fileExtension, property);
        final String bucketName = ObjectStorageConfig.BUCKET_NAME;
        final String folderName = property.getFolderName();
        final String fileName = createFileName(targetName, folderName, fileExtension);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(
                    new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new UploadFailException();
        }

        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    private void checkValidExtension(final String fileExtension, MediaProperty property) {
        if (property == MediaProperty.STADIUM_SEAT
                || property == MediaProperty.STADIUM_SEAT_LABEL) {
            checkValidSeatExtension(fileExtension);
        } else {
            checkValidImageExtension(fileExtension);
        }
    }

    private void checkValidSeatExtension(final String fileExtension) {
        if (!StadiumSeatMediaExtension.isValid(fileExtension)) {
            throw new InvalidExtensionException(fileExtension);
        }
    }

    private void checkValidImageExtension(final String fileExtension) {
        if (!ImageExtension.isValid(fileExtension)) {
            throw new InvalidExtensionException(fileExtension);
        }
    }

    private String createFileName(
            final String targetName, final String folderName, final String fileExtension) {
        return folderName + "/" + targetName + "." + fileExtension;
    }
}
