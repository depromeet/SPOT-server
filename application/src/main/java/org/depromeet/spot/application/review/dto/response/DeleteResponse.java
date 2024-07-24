package org.depromeet.spot.application.review.dto.response;

public record DeleteResponse(Long deletedReviewId) {
    public static DeleteResponse from(Long id) {
        return new DeleteResponse(id);
    }
}
