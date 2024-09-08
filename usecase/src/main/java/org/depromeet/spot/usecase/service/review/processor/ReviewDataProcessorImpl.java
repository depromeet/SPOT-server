package org.depromeet.spot.usecase.service.review.processor;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewLocationInfo;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase.UpdateReviewCommand;
import org.depromeet.spot.usecase.port.out.block.BlockRepository;
import org.depromeet.spot.usecase.port.out.block.BlockRowRepository;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
import org.depromeet.spot.usecase.port.out.section.SectionRepository;
import org.depromeet.spot.usecase.port.out.stadium.StadiumRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewDataProcessorImpl implements ReviewDataProcessor {
    private final SeatRepository seatRepository;
    private final BlockRepository blockRepository;
    private final StadiumRepository stadiumRepository;
    private final SectionRepository sectionRepository;
    private final BlockRowRepository blockRowRepository;

    @Override
    public Seat getSeat(Long blockId, Integer seatNumber) {
        if (seatNumber == null) {
            return null;
        }
        return seatRepository.findByBlockIdAndSeatNumber(blockId, seatNumber);
    }

    @Override
    public Block getBlock(Long blockId) {
        return blockRepository.findById(blockId);
        // TODO: 예외처리 어떻게 할 건지 논의
        //            .orElseThrow(() -> new IllegalArgumentException("Block not found with id: " +
        // blockId));
    }

    @Override
    public Stadium getStadium(Long stadiumId) {
        return stadiumRepository.findById(stadiumId);
        // TODO: 예외처리 어떻게 할 건지 논의
        //            .orElseThrow(() -> new IllegalArgumentException("Stadium not found with id: "
        // + stadiumId));
    }

    @Override
    public Section getSection(Long sectionId) {
        return sectionRepository.findById(sectionId);
        // TODO: 예외처리 어떻게 할 건지 논의
        //            .orElseThrow(() -> new IllegalArgumentException("Section not found with id: "
        // + sectionId));
    }

    @Override
    public BlockRow getBlockRow(Long stadiumId, String blockCode, Integer rowNumber) {
        if (rowNumber == null) {
            return null;
        }
        return blockRowRepository.findBy(stadiumId, blockCode, rowNumber);
        // TODO: 예외처리 어떻게 할 건지 논의
        //            .orElseThrow(() -> new IllegalArgumentException("BlockRow not found with
        // stadiumId: " + stadiumId + ", blockCode: " + blockCode + ", rowNumber: " + rowNumber));
    }

    @Override
    public BlockRow getBlockRow(Long blockId, Integer rowNumber) {
        if (rowNumber == null) {
            return null;
        }
        return blockRowRepository.findBy(blockId, rowNumber);
        // TODO: 예외처리 어떻게 할 건지 논의
        //            .orElseThrow(() -> new IllegalArgumentException("BlockRow not found with
        // stadiumId: " + stadiumId + ", blockCode: " + blockCode + ", rowNumber: " + rowNumber));
    }

    @Override
    public Seat getSeatWithDetails(Long blockId, Integer seatNumber) {
        Seat seat = getSeat(blockId, seatNumber);
        if (seat == null) {
            return null;
        }
        Block block = getBlock(seat.getBlock().getId());
        Stadium stadium = getStadium(block.getStadiumId());
        Section section = getSection(block.getSectionId());
        BlockRow blockRow =
                getBlockRow(stadium.getId(), block.getCode(), seat.getRow().getNumber());

        return Seat.builder()
                .id(seat.getId())
                .stadium(stadium)
                .section(section)
                .block(block)
                .row(blockRow)
                .seatNumber(seat.getSeatNumber())
                .build();
    }

    @Override
    public ReviewLocationInfo getUpdatedLocationInfo(
            Review existingReview, UpdateReviewCommand command) {
        Block block = existingReview.getBlock();
        Section section = existingReview.getSection();
        BlockRow row = existingReview.getRow();
        Seat seat = existingReview.getSeat();

        boolean blockChanged = !existingReview.getBlock().getId().equals(command.blockId());
        boolean rowChanged = isRowChanged(existingReview.getRow(), command.rowNumber());
        boolean seatChanged = isSeatChanged(existingReview.getSeat(), command.seatNumber());

        if (command.seatNumber() != null) {
            if (seatChanged || blockChanged) {
                seat = getSeatWithDetails(command.blockId(), command.seatNumber());
                block = seat.getBlock();
                section = seat.getSection();
                row = seat.getRow();
            }
        } else {
            if (rowChanged || blockChanged) {
                if (command.rowNumber() != null) {
                    row = getBlockRow(command.blockId(), command.rowNumber());
                    block = row.getBlock();
                } else {
                    row = null;
                    if (blockChanged) {
                        block = getBlock(command.blockId());
                    }
                }
                section = getSection(block.getSectionId());
            }
            seat = null;
        }

        return new ReviewLocationInfo(section, block, row, seat);
    }

    private boolean isRowChanged(BlockRow existingRow, Integer newRowNumber) {
        return (newRowNumber != null
                        && (existingRow == null || !existingRow.getNumber().equals(newRowNumber)))
                || (newRowNumber == null && existingRow != null);
    }

    private boolean isSeatChanged(Seat existingSeat, Integer newSeatNumber) {
        return (newSeatNumber != null
                        && (existingSeat == null
                                || !existingSeat.getSeatNumber().equals(newSeatNumber)))
                || (newSeatNumber == null && existingSeat != null);
    }

    @Override
    public Stadium getStadiumIfChanged(Review existingReview, Long newStadiumId) {
        if (!existingReview.getStadium().getId().equals(newStadiumId)) {
            return stadiumRepository.findById(newStadiumId);
        }
        return existingReview.getStadium();
    }
}
