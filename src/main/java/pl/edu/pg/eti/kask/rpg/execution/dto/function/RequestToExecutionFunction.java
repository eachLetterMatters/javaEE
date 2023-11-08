package pl.edu.pg.eti.kask.rpg.execution.dto.function;

import pl.edu.pg.eti.kask.rpg.execution.dto.PutExecutionRequest;
import pl.edu.pg.eti.kask.rpg.execution.entity.Execution;

import java.util.UUID;
import java.util.function.BiFunction;


public class RequestToExecutionFunction implements BiFunction<UUID, PutExecutionRequest, Execution> {

    @Override
    public Execution apply(UUID id, PutExecutionRequest request) {
        return Execution.builder()
                .id(id)
                .date(request.getDate())
                .isDone(request.isDone())
                .userComment(request.getUserComment())
                .build();
    }

}
