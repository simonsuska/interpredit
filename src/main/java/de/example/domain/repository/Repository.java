package de.example.domain.repository;

public interface Repository {
    void open(String content);
    void save(String content);
    void delete();
}
