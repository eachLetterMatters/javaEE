package pl.edu.pg.eti.kask.rpg.execution.dto.function;

import pl.edu.pg.eti.kask.rpg.execution.dto.GetExecutionsResponse;
import pl.edu.pg.eti.kask.rpg.execution.entity.Execution;

import java.util.List;
import java.util.function.Function;


public class ExecutionsToResponseFunction implements Function<List<Execution>, GetExecutionsResponse> {

    @Override
    public GetExecutionsResponse apply(List<Execution> entities) {
        return GetExecutionsResponse.builder()
                .executions(entities.stream()
                        .map(execution -> GetExecutionsResponse.Execution.builder()
                                .id(execution.getId())
                                .date(execution.getDate())
                                .build())
                        .toList())
                .build();
    }

}
