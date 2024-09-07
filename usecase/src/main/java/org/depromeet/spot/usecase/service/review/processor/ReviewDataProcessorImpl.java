package org.depromeet.spot.usecase.service.review.processor;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;
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
}
