package de.example.domain.entities.machines;

import de.example.domain.entities.Status;

/** A type which provides the ability to execute a command on a machine. */
public interface Command {
    Status execute(Machine machine);
}
