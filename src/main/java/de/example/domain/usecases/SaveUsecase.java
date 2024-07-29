package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.repository.Repository;

import java.util.Objects;
import java.util.function.Function;

public class SaveUsecase implements Function<String, Boolean> {
    private final Repository repository;

    @Inject
    public SaveUsecase(@Named(Di.REPOSITORY) Repository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Boolean apply(String content) {
        return this.repository.save(content);
    }
}
