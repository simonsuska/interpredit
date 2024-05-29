package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.Di;
import de.example.domain.entities.exit.builder.ExitStatus;
import de.example.domain.repository.Repository;

import java.util.Objects;
import java.util.function.Function;

public class OpenUsecase implements Function<String, ExitStatus> {
    private Repository repository;

    @Inject
    public OpenUsecase(@Named(Di.REPOSITORY) Repository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public ExitStatus apply(String filename) {
        // TODO: Implement
        return null;
    }
}
