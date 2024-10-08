package org.depromeet.spot.application.media;

import jakarta.validation.Valid;

import org.depromeet.spot.application.common.annotation.CurrentMember;
import org.depromeet.spot.application.media.dto.request.CreatePresignedUrlRequest;
import org.depromeet.spot.application.media.dto.response.MediaUrlResponse;
import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.usecase.port.out.media.CreatePresignedUrlPort;
import org.depromeet.spot.usecase.port.out.media.CreatePresignedUrlPort.PresignedUrlRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "미디어 (이미지, 영상)")
public class MediaController {

    private final CreatePresignedUrlPort createPresignedUrlPort;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/reviews/images")
    @Operation(summary = "리뷰 이미지 업로드 url을 생성합니다.")
    public MediaUrlResponse createReviewImageUploadUrl(
            @Parameter(hidden = true) Long memberId,
            @RequestBody @Valid CreatePresignedUrlRequest request) {
        PresignedUrlRequest command =
                new PresignedUrlRequest(request.fileExtension(), MediaProperty.REVIEW);
        String presignedUrl = createPresignedUrlPort.forImage(memberId, command);
        return new MediaUrlResponse(presignedUrl);
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/members/profile/images")
    @Operation(summary = "유저의 프로필 이미지 업로드 url을 생성합니다.")
    public MediaUrlResponse createProfileImageUploadUrl(
            @Parameter(hidden = true) Long memberId,
            @RequestBody @Valid CreatePresignedUrlRequest request) {
        PresignedUrlRequest command =
                new PresignedUrlRequest(request.fileExtension(), MediaProperty.PROFILE_IMAGE);
        String presignedUrl = createPresignedUrlPort.forImage(memberId, command);
        return new MediaUrlResponse(presignedUrl);
    }
}
