package de.example.domain.entities.machines.ram;

import de.example.domain.entities.Status;
import de.example.domain.entities.machines.Command;
import de.example.domain.entities.machines.Machine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RandomAccessMachineCommand implements Command {
    private final String name;
    private final int value;

    public RandomAccessMachineCommand(String name, int value) {
        this.name = name.toLowerCase();
        this.value = value;
    }

    @Override
    public Status execute(Machine machine) {
        Status status;

        try {
            Method method = machine.getClass().getMethod(name, Integer.TYPE);
            status = (Status)method.invoke(machine, value);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            status = Status.COMMAND_ERROR;
        }

        return status;
    }
}
