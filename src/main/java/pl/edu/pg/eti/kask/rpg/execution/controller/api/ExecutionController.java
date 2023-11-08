package pl.edu.pg.eti.kask.rpg.execution.controller.api;

import pl.edu.pg.eti.kask.rpg.execution.dto.GetExecutionResponse;
import pl.edu.pg.eti.kask.rpg.execution.dto.GetExecutionsResponse;
import pl.edu.pg.eti.kask.rpg.execution.dto.PutExecutionRequest;

import java.io.InputStream;
import java.util.UUID;


public interface ExecutionController {

    GetExecutionsResponse getExecutions();

    // GetCharactersResponse getProfessionCharacters(UUID id);

    GetExecutionsResponse getUserExecutions(UUID id);

    GetExecutionResponse getExecution(UUID uuid);

    void putExecution(UUID id, PutExecutionRequest request);

    void deleteExecution(UUID id);

}
