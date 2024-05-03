package de.example.domain.usecases;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.repository.Repository;

import java.util.function.Supplier;

public class DeleteUsecase implements Supplier<ExitStatus> {
    private Repository repository;

    @Override
    public ExitStatus get() {
        // TODO: Implement
        return null;
    }
}
