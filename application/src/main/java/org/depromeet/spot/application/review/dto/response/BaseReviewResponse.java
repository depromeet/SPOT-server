package org.depromeet.spot.application.review.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;

public record BaseReviewResponse(
        Long id,
        MemberInfo member,
        StadiumResponse stadium,
        SectionResponse section,
        BlockResponse block,
        RowResponse row,
        SeatResponse seat,
        LocalDateTime dateTime,
        String content,
        List<ReviewImageResponse> images,
        List<KeywordResponse> keywords) {

    public static BaseReviewResponse from(Review review) {
        return new BaseReviewResponse(
                review.getId(),
                MemberInfo.from(review.getMember()),
                StadiumResponse.from(review.getStadium()),
                SectionResponse.from(review.getSection()),
                BlockResponse.from(review.getBlock()),
                RowResponse.from(review.getRow()),
                SeatResponse.from(review.getSeat()),
                review.getDateTime(),
                review.getContent(),
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

    public record RowResponse(Long id, Integer number) {
        public static RowResponse from(BlockRow row) {
            return new RowResponse(row.getId(), row.getNumber());
        }
    }

    public record SeatResponse(Long id, Integer seatNumber) {
        public static SeatResponse from(Seat seat) {
            return new SeatResponse(seat.getId(), seat.getSeatNumber());
        }
    }

    public record MemberInfo(String profileImage, String nickname, Integer level) {
        public static MemberInfo from(Member member) {
            return new MemberInfo(
                    member.getProfileImage(), member.getNickname(), member.getLevel());
        }
    }
}
