package de.example.domain.entities.machines.ram;

import de.example.domain.entities.exit.builder.ExitStatus;
import de.example.domain.entities.machines.Command;
import de.example.domain.entities.machines.Decoder;
import io.vavr.control.Either;

public class RandomAccessMachineDecoder implements Decoder {
    @Override
    public Either<ExitStatus, Command> decode(String command) {
        // TODO: Implement
        return null;
    }
}
