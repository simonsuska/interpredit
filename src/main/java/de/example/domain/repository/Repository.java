package de.example.domain.repository;

public interface Repository {
    String open(String filename);
    boolean close();
    boolean save(String content);
    boolean delete();
}
