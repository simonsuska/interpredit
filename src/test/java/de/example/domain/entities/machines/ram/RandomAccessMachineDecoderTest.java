package de.example.domain.entities.machines.ram;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.machines.Command;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomAccessMachineDecoderTest {
    @Test
    void decode() {
        RandomAccessMachineDecoder decoder = new RandomAccessMachineDecoder();
        Either<ExitStatus, Command> result;

        result = decoder.decode(null);
        assertTrue(result.isLeft());

        result = decoder.decode("");
        assertTrue(result.isLeft());

        result = decoder.decode("CMD");
        assertTrue(result.isLeft());

        result = decoder.decode("CMD A");
        assertTrue(result.isLeft());

        result = decoder.decode("CMD 1");
        assertTrue(result.isRight());

        result = decoder.decode(" CMD 1  ");
        assertTrue(result.isRight());

        result = decoder.decode(" CMD   1  ");
        assertTrue(result.isRight());
    }
}
