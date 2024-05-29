package de.example.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BufferTest {
    /** This test evaluates whether reading an empty buffer works properly. */
    @Test
    void readEmpty() {
        Buffer<Integer> buffer = new Buffer<>(174);
        Integer result = buffer.read();
        assertEquals(result, 174);
    }

    /** This test evaluates whether reading a filled buffer works properly. */
    @Test
    void readFilled() {
        Buffer<Integer> buffer = new Buffer<>();
        Integer result = buffer.read();
        assertNull(result);
    }

    /** This test evaluates whether writing a new data object works properly. */
    @Test
    void write() {
        Buffer<Integer> buffer = new Buffer<>();
        buffer.write(203);
        assertEquals(buffer.read(), 203);
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
