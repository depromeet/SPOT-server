package org.depromeet.spot.usecase.service.block;

import java.util.Comparator;
import java.util.List;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase;
import org.depromeet.spot.usecase.port.in.block.CreateRowUsecase;
import org.depromeet.spot.usecase.port.out.block.BlockRowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateRowService implements CreateRowUsecase {

    private final BlockRowRepository blockRowRepository;
    private final BlockReadUsecase blockReadUsecase;

    @Override
    public void createAll(Block block, List<CreateRowCommand> commands) {
        blockReadUsecase.checkExistsById(block.getId());
        List<BlockRow> rows =
                blockRowRepository.createAll(
                        commands.stream()
                                .map(
                                        command ->
                                                BlockRow.builder()
                                                        .block(block)
                                                        .number(command.number())
                                                        .maxSeats(command.maxSeatNum())
                                                        .build())
                                .sorted(Comparator.comparingInt(BlockRow::getNumber))
                                .toList());
        // TODO: Seat로 넘겨서 좌석 생성

    }
}
