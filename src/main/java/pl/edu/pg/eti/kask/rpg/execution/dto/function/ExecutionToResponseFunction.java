package pl.edu.pg.eti.kask.rpg.execution.dto.function;

import pl.edu.pg.eti.kask.rpg.execution.dto.GetExecutionResponse;
import pl.edu.pg.eti.kask.rpg.execution.entity.Execution;

import java.util.function.Function;


public class ExecutionToResponseFunction implements Function<Execution, GetExecutionResponse> {

    @Override
    public GetExecutionResponse apply(Execution entity) {
        return GetExecutionResponse.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .isDone(entity.isDone())
                .userComment(entity.getUserComment())
                .build();
    }

}
