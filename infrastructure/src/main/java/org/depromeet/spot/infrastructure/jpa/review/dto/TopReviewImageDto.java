package org.depromeet.spot.infrastructure.jpa.review.dto;

public record TopReviewImageDto(
        String url, Long reviewId, String blockCode, Integer rowNumber, Integer seatNumber) {}
