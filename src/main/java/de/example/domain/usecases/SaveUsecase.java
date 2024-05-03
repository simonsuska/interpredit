package de.example.domain.usecases;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.repository.Repository;

import java.util.function.Function;

public class SaveUsecase implements Function<String, ExitStatus> {
    private Repository repository;

    @Override
    public ExitStatus apply(String content) {
        // TODO: Implement
        return null;
    }
}
