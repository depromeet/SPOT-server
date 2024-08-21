package org.depromeet.spot.usecase.service.review.processor;

import java.util.ArrayList;
import java.util.List;

import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.image.ReviewImage;
import org.depromeet.spot.usecase.port.out.media.ImageUploadPort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewImageProcessor {

    private final ImageUploadPort imageUploadPort;

    public List<String> getImageUrl(List<MultipartFile> images) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile image : images) {
            String name = image.getOriginalFilename();
            urls.add(imageUploadPort.upload(name, image, MediaProperty.REVIEW));
        }
        return urls;
    }

    public void processImages(Review review, List<String> imageUrls) {
        for (String url : imageUrls) {
            ReviewImage image = ReviewImage.create(null, review, url);
            review.addImage(image);
        }
    }
}
