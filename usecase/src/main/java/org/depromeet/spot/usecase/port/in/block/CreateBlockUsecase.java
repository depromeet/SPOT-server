package org.depromeet.spot.usecase.port.in.block;

import java.util.List;

import org.depromeet.spot.usecase.port.in.block.CreateRowUsecase.CreateRowCommand;

public interface CreateBlockUsecase {

    void create(Long stadiumId, Long sectionId, CreateBlockCommand command);

    record CreateBlockCommand(String code, int maxRows, List<CreateRowCommand> rowInfos) {}
}
