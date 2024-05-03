package de.example.domain.entities.machines;

import de.example.domain.entities.exit.ExitStatus;

public interface Command {
    ExitStatus execute();
}
