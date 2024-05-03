package de.example.domain.usecases;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.repository.Repository;

import java.nio.file.Path;
import java.util.function.Function;

public class OpenUsecase implements Function<Path, ExitStatus> {
    private Repository repository;

    @Override
    public ExitStatus apply(Path filepath) {
        // TODO: Implement
        return null;
    }
}
