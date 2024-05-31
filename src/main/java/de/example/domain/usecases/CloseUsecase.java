package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.Di;
import de.example.domain.entities.exit.builder.ExitStatus;
import de.example.domain.repository.Repository;

import java.util.Objects;
import java.util.function.Supplier;

public class CloseUsecase implements Supplier<ExitStatus> {
    private Repository repository;

    @Inject
    public CloseUsecase(@Named(Di.REPOSITORY) Repository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public ExitStatus get() {
        // TODO: Implement
        return null;
    }
}
