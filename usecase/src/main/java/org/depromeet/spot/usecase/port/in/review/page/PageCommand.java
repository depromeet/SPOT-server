package org.depromeet.spot.usecase.port.in.review.page;

import org.depromeet.spot.domain.review.Review.SortCriteria;

import lombok.Builder;

@Builder
public record PageCommand(String cursor, SortCriteria sortBy, Integer size) {}
