package de.example.domain.entities;

/**
 * This type stores a single generic data object
 * and grants read and write access to it.
 * @param <T> The type of the data object to be stored
 */
public class Buffer<T> {
    private T value;

    /** This constructor creates an empty buffer. */
    public Buffer() {
        this.value = null;
    }

    /**
     * This constructor creates a buffer and stores the
     * given data object in it.
     * @param data The data object to be stored
     */
    public Buffer(T data) {
        this.value = data;
    }

    /**
     * This method returns the stored data object or
     * `null`, if the buffer is empty.
     * @return The stored data object or `null`, if
     *         the buffer is empty
     */
    public T read() {
        return this.value;
    }

    /**
     * This method stores the given data object in the buffer.
     * @param data The data object to be stored
     */
    public void write(T data) {
        this.value = data;
    }

    /**
     * This method checks whether the buffer is empty or not.
     * @return `true` if the buffer is empty, otherwise `false`
     */
    public boolean isEmpty() {
        return value == null;
    }
}
