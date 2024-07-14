package org.depromeet.spot.domain.review;

import java.util.List;

public record ReviewListResult(
        List<Review> reviews,
        List<KeywordCount> topKeywords,
        int totalCount,
        int offset,
        int limit) {}
