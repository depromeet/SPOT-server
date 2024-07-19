package org.depromeet.spot.application.review.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.application.member.dto.response.MemberReviewProfileResponse;
import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;

public record BaseReviewResponse(
        Long id,
        MemberReviewProfileResponse member,
        StadiumResponse stadium,
        SectionResponse section,
        BlockResponse block,
        SeatResponse seat,
        LocalDateTime dateTime,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ReviewImageResponse> images,
        List<KeywordResponse> keywords) {
    public static BaseReviewResponse from(Review review, Member member, Seat seat) {
        return new BaseReviewResponse(
                review.getId(),
                MemberReviewProfileResponse.from(member),
                StadiumResponse.from(seat.getStadium()),
                SectionResponse.from(seat.getSection()),
                BlockResponse.from(seat.getBlock()),
                SeatResponse.from(seat),
                review.getDateTime(),
                review.getContent(),
                review.getCreatedAt(),
                review.getUpdatedAt(),
                review.getImages().stream()
                        .map(ReviewImageResponse::from)
                        .collect(Collectors.toList()),
                review.getKeywords().stream()
                        .map(KeywordResponse::from)
                        .collect(Collectors.toList()));
    }

    public record StadiumResponse(Long id, String name) {
        public static StadiumResponse from(Stadium stadium) {
            return new StadiumResponse(stadium.getId(), stadium.getName());
        }
    }

    public record SectionResponse(Long id, String name, String alias) {
        public static SectionResponse from(Section section) {
            return new SectionResponse(section.getId(), section.getName(), section.getAlias());
        }
    }

    public record BlockResponse(Long id, String code) {
        public static BlockResponse from(Block block) {
            return new BlockResponse(block.getId(), block.getCode());
        }
    }

    public record SeatResponse(Long id, Integer seatNumber) {
        public static SeatResponse from(Seat seat) {
            return new SeatResponse(seat.getId(), seat.getSeatNumber());
        }
    }
}
