package de.example.data.repository;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.Di;
import de.example.data.datasources.MutableDatasource;
import de.example.domain.repository.Repository;

import java.util.Objects;

public class RepositoryImpl implements Repository {
    private final MutableDatasource datasource;

    @Inject
    public RepositoryImpl(@Named(Di.MUTABLE_DATASOURCE) MutableDatasource datasource) {
        this.datasource = Objects.requireNonNull(datasource);
    }

    @Override
    public String open(String filename) {
        // TODO: Implement
        return null;
    }

    @Override
    public void save(String content) {
        // TODO: Implement
    }

    @Override
    public void delete() {
        // TODO: Implement
    }
}
