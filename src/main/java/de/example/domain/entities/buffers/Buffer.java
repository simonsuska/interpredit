package de.example.domain.entities.buffers;

public interface Buffer<T> {
    T read();
    void write(T data);
    boolean isEmpty();
}
