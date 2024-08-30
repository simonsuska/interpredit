package de.example.domain.entities;

import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class BufferTest {
    @Test
    void readFilled() throws InterruptedException {
        Buffer<Integer> buffer = new Buffer<>(174);
        Integer result = buffer.read();
        assertEquals(result, 174);
    }

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
