package de.example.domain.entities.machines.ram;

import de.example.domain.entities.machines.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RandomAccessMachineDecoderTest {
    @Test
    void decode() {
        RandomAccessMachineDecoder decoder = new RandomAccessMachineDecoder();
        Command result;

        result = decoder.decode(null);
        assertNull(result);

        result = decoder.decode("");
        assertNull(result);

        result = decoder.decode("CMD");
        assertNull(result);

        result = decoder.decode("CMD A");
        assertNull(result);

        result = decoder.decode("CMD 1");
        assertNotNull(result);

        result = decoder.decode("CMD -1");
        assertNotNull(result);

        result = decoder.decode(" CMD 1  ");
        assertNotNull(result);

        result = decoder.decode(" CMD   1  ");
        assertNotNull(result);
    }
}
