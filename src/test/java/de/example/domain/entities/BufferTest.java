package de.example.domain.entities;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

// TODO: Adjust doc
class BufferTest {
    /** This test evaluates whether reading an empty buffer works properly. */
    @Test
    void readFilled() throws InterruptedException {
        Buffer<Integer> buffer = new Buffer<>(174);
        Integer result = buffer.read();
        assertEquals(result, 174);
    }

    /** This test evaluates whether reading a filled buffer works properly. */
    @Test
    void readEmpty() throws InterruptedException {
        Buffer<Integer> buffer = new Buffer<>();

        new Thread(() -> {
            try {
                Integer result = buffer.read();
                assertEquals(result, 203);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        TimeUnit.MILLISECONDS.sleep(100);
        buffer.write(203);
    }

    @Test
    void write() throws InterruptedException {
        Buffer<Integer> buffer = new Buffer<>();
        buffer.write(174);

        Integer result = buffer.read();
        assertEquals(result, 174);
    }

    /** This test evaluates whether checking an empty buffer works properly. */
    @Test
    void isEmptyTrue() {
        Buffer<Integer> buffer = new Buffer<>();
        boolean result = buffer.isEmpty();
        assertTrue(result);
    }

    /** This test evaluates whether checking a filled buffer works properly. */
    @Test
    void isEmptyFalse() {
        Buffer<Integer> buffer = new Buffer<>(174);
        boolean result = buffer.isEmpty();
        assertFalse(result);
    }
}
