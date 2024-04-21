package de.example.domain.repository;

import java.io.File;

public interface Repository {
    void save(String content);
    void delete();
}
