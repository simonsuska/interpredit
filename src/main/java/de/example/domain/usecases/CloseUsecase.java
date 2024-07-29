package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.repository.Repository;

import java.util.Objects;
import java.util.function.Supplier;

public class CloseUsecase implements Supplier<Boolean> {
    private final Repository repository;

    @Inject
    public CloseUsecase(@Named(Di.REPOSITORY) Repository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Boolean get() {
        return this.repository.close();
    }
}
