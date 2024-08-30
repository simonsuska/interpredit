package de.example.domain.repository;

/** A type that defines the communication between the data and domain layer. */
public interface Repository {
    String open(String filename);
    boolean close();
    boolean save(String content);
    boolean delete();
}
