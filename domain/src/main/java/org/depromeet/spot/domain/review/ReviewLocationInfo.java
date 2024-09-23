package org.depromeet.spot.domain.review;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;

public record ReviewLocationInfo(Section section, Block block, BlockRow row, Seat seat) {}
