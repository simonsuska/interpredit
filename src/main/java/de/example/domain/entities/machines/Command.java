package de.example.domain.entities.machines;

import de.example.core.exceptions.CommandExecutionException;
import de.example.domain.entities.ExitStatus;

public interface Command {
    ExitStatus execute() throws CommandExecutionException;
}
