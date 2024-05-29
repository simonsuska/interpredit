package de.example.domain.entities.machines;

import de.example.domain.entities.exit.builder.ExitStatus;
import io.vavr.control.Either;

public interface Decoder {
    Either<ExitStatus, Command> decode(String command);
}
