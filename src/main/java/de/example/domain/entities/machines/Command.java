package de.example.domain.entities.machines;

import de.example.domain.entities.Status;

public interface Command {
    Status execute(Machine machine);
}
