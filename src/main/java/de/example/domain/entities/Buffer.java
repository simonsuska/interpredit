package de.example.domain.entities;

public class Buffer<T> {
    private T value;

    public Buffer() {
        this.value = null;
    }

    public Buffer(T value) {
        this.value = value;
    }

    public T read() {
        // TODO: Implement
        return null;
    }

    public void write(T data) {
        // TODO: Implement
    }

    public boolean isEmpty() {
        // TODO: Implement
        return false;
    }
}
