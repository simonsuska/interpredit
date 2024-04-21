package de.example.domain.entities.machines.ram;

import de.example.core.exceptions.CommandExecutionException;
import de.example.domain.entities.ExitStatus;
import de.example.domain.entities.machines.Command;

public class RandomAccessMachineCommand implements Command {
    private final String name;
    private final int value;

    public RandomAccessMachineCommand(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public ExitStatus execute() throws CommandExecutionException {
        // TODO: Implement
        return null;
    }
}
