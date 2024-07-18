package org.depromeet.spot.application.block.dto.request;

import java.util.List;

import org.depromeet.spot.usecase.port.in.block.CreateBlockUsecase.CreateBlockCommand;
import org.depromeet.spot.usecase.port.in.block.CreateRowUsecase.CreateRowCommand;

public record CreateBlockRequest(String code, int maxRows, List<CreateRowRequest> rowInfos) {

    public record CreateRowRequest(int number, int maxSeatNum) {
        public CreateRowCommand toCommand() {
            return CreateRowCommand.builder().number(number).maxSeatNum(maxSeatNum).build();
        }
    }

    public CreateBlockCommand toCommand() {
        List<CreateRowCommand> rowCommands =
                rowInfos.stream().map(CreateRowRequest::toCommand).toList();
        return new CreateBlockCommand(code, maxRows, rowCommands);
    }
}
