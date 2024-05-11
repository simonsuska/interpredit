package de.example.domain.repository;

public interface Repository {
    String open(String filename);
    void save(String content);
    void delete();
}
