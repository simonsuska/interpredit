package de.example.domain.entities.machines.ram;

import de.example.domain.entities.Status;
import de.example.domain.entities.machines.Command;
import de.example.domain.entities.machines.Machine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This type represents a decoded command for a {@code RandomAccessMachine}
 * and provides the ability to execute it.
 */
public class RandomAccessMachineCommand implements Command {

    //: SECTION: - ATTRIBUTES

    /**
     * This attribute stores the name of the command which must be equal to the
     * name of the method of the {@code RandomAccessMachine} class.
     * */
    private final String name;
    private final int value;

    /**
     * This attribute stores whether the command is user-generated or not.
     *
     * <br><br><b>Discussion</b><br>
     * It is used to distinguish user-generated and machine-generated HOP commands.
     * User-generated HOP commands are regarded as errors as it does not represent
     * an actual random access machine command. Machine-generated HOP commands are
     * regarded as valid and are constructed if the user enters a line that does
     * not contain any text.
     */
    private final boolean isUserGenerated;

    //: SECTION: - CONSTRUCTORS

    public RandomAccessMachineCommand(String name, int value, boolean isUserGenerated) {
        this.name = name.toLowerCase();
        this.value = value;
        this.isUserGenerated = isUserGenerated;
    }

    public RandomAccessMachineCommand(String name, int value) {
        this(name, value, true);
    }

    //: SECTION: - METHODS

    /**
     * This method executes this command on the given machine.
     *
     * @param machine The machine to execute the command on
     * @return The status after the command has been executed
     */
    @Override
    public Status execute(Machine machine) {
        Status status;

        if (this.name.equals("hop") && this.isUserGenerated)
            return Status.COMMAND_ERROR;

        try {
            Method method = machine.getClass().getMethod(name, Integer.TYPE);
            status = (Status)method.invoke(machine, value);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            status = Status.COMMAND_ERROR;
        }

        return status;
    }
}
