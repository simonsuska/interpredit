package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.repository.Repository;

import java.util.Objects;
import java.util.function.Function;

public class OpenUsecase implements Function<String, String> {
    private final Repository repository;

    @Inject
    public OpenUsecase(@Named(Di.REPOSITORY) Repository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public String apply(String filename) {
        return this.repository.open(filename);
    }
}
