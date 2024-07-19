package org.depromeet.spot.domain.review;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.stadium.Stadium;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Review {
    private final Long id;
    private final Member member;
    private final Stadium stadium;
    private final Block block;
    private final BlockRow row;
    private final Seat seat;
    private final LocalDateTime dateTime;
    private final String content;
    private final LocalDateTime deletedAt;
    private final List<ReviewImage> images;
    private final List<ReviewKeyword> keywords;
}
