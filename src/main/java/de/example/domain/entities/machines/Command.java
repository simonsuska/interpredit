package de.example.domain.entities.machines;

import de.example.domain.entities.exit.builder.ExitStatus;

public interface Command {
    ExitStatus execute(Machine machine);
}
