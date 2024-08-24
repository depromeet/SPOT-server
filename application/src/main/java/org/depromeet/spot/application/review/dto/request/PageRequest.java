package org.depromeet.spot.application.review.dto.request;

import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.usecase.port.in.review.page.PageCommand;

import io.swagger.v3.oas.annotations.Parameter;

public record PageRequest(
        @Parameter(description = "다음 페이지 커서") String cursor,
        @Parameter(description = "정렬 기준", example = "DATE_TIME") SortCriteria sortBy,
        @Parameter(description = "페이지 크기") Integer size) {

    public PageCommand toCommand() {
        return PageCommand.builder().cursor(cursor).sortBy(sortBy).size(size).build();
    }
}
