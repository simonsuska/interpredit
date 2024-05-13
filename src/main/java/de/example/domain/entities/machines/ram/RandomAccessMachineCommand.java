package de.example.domain.entities.machines.ram;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.machines.Command;
import de.example.domain.entities.machines.Machine;

public class RandomAccessMachineCommand implements Command {
    private final String name;
    private final int value;

    public RandomAccessMachineCommand(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public ExitStatus execute(Machine machine) {
        // TODO: Implement
        return null;
    }
}
