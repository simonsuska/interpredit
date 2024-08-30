package de.example.domain.entities;

/**
 * This type stores a single generic data object and grants read and write access to it.
 *
 * <br><br><b>Discussion</b><br>
 * In the context of Interpredit, this buffer is used of type {@code Buffer<String>} by the random access machine for
 * the input and output of values.
 * <br><br>
 * In the context of Interpredit, this class is a passive one, as its state is accessed from multiple threads. The
 * runner thread accesses the state to execute the OUT and INP commands and to restore the original state after a
 * program execution. The main thread also accesses the state by writing the entered value to the buffer after a
 * user input. Finally, the printer thread also uses the state to output the corresponding value on the console after
 * an OUT command. For this reason, all methods that access the {@code value} or {@code isClosed} attributes are
 * synchronized. The monitor here is the object itself.
 *
 * @param <T> The type of the data object to be stored
 */
public class Buffer<T> {

    //: SECTION: - ATTRIBUTES

    /** This attribute represents the value of the buffer. */
    private T value;

    /** This attribute stores whether the buffer is closed or not. */
    private boolean isClosed;

    //: SECTION: - CONSTRUCTORS

    /** This constructor creates an empty buffer. */
    public Buffer() {
        this.value = null;
        this.isClosed = false;
    }

    /**
     * This constructor creates a buffer and stores the given data object in it.
     *
     * @param data The data object to be stored
     */
    public Buffer(T data) {
        this.value = data;
    }

    //: SECTION: - METHODS

    /**
     * This method returns the stored data object and subsequently clears the buffer or returns {@code null}, if the
     * buffer was closed.
     *
     * <br><br><b>Discussion</b><br>
     * Note that this method is blocking, which means it blocks the corresponding thread as long as the buffer is empty.
     * It only continues when the buffer has either been filled or closed.
     * <br><br>
     * In the context of Interpredit, the blocking mechanism is used to await an input from the user.
     *
     * @return The stored data object or {@code null}, if the buffer was closed
     */
    public synchronized T read() throws InterruptedException {
        while (this.value == null && !this.isClosed)
            this.wait();

        if (!this.isClosed) {
            T value = this.value;
            this.value = null;
            return value;
        }

        return null;
    }

    /**
     * This method stores the given data object in the buffer.
     *
     * <br><br><b>Discussion</b><br>
     * In the context of Interpredit, this method is used to write the buffer which causes the program to continue if
     * it has been waiting for input from the user.
     *
     * @param data The data object to be stored
     */
    public synchronized void write(T data) {
        this.value = data;
        this.notify();
    }

    /**
     * This method closes the buffer.
     *
     * <br><br><b>Discussion</b><br>
     * In the context of Interpredit, this method is used to close the buffer and cause the program to continue if
     * it has been waiting for input from the user.
     */
    public synchronized void close() {
        this.isClosed = true;
        this.notify();
    }

    /**
     * This method checks whether the buffer is empty or not.
     *
     * @return {@code true} if the buffer is empty, otherwise {@code false}
     */
    public synchronized boolean isEmpty() {
        return value == null;
    }

    /** This method resets the buffer, which is clearing and opening it. */
    public synchronized void reset() {
        this.value = null;
        this.isClosed = false;
    }
}
