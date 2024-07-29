package de.example.domain.entities;

// TODO: Adjust doc
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

    // TODO: Adjust doc
    /**
     * This method returns the stored data object or `null`, if
     * the buffer is empty and subsequently clears the buffer.
     * @return The stored data object or `null`, if
     *         the buffer is empty
     */
    public synchronized T read() throws InterruptedException {
        while (this.value == null)
            this.wait();

        T value = this.value;
        this.value = null;
        return value;
    }

    // TODO: Adjust doc
    /**
     * This method stores the given data object in the buffer.
     * @param data The data object to be stored
     */
    public synchronized void write(T data) {
        this.value = data;
        this.notify();
    }

    // TODO: Adjust doc
    /**
     * This method checks whether the buffer is empty or not.
     * @return `true` if the buffer is empty, otherwise `false`
     */
    public synchronized boolean isEmpty() {
        return value == null;
    }
}
