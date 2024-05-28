package de.example.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BufferTest {
    @Test
    void readEmpty() {
        Buffer<Integer> buffer = new Buffer<>(174);
        Integer result = buffer.read();
        assertEquals(result, 174);
    }

    @Test
    void readFilled() {
        Buffer<Integer> buffer = new Buffer<>();
        Integer result = buffer.read();
        assertNull(result);
    }

    @Test
    void write() {
        Buffer<Integer> buffer = new Buffer<>();
        buffer.write(203);
        assertEquals(buffer.read(), 203);
    }

    @Test
    void isEmptyTrue() {
        Buffer<Integer> buffer = new Buffer<>();
        boolean result = buffer.isEmpty();
        assertTrue(result);
    }

    @Test
    void isEmptyFalse() {
        Buffer<Integer> buffer = new Buffer<>(174);
        boolean result = buffer.isEmpty();
        assertFalse(result);
    }
}
