package de.example.domain.entities.machines.ram;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.exit.status.Status;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class RandomAccessMachineCommandTest {
    @Mock
    private RandomAccessMachine ram;

    private static final int SET_VALUE = 1;
    private static final int INP_VALUE = 2;
    private static final int OUT_VALUE = 3;
    private static final int HLT_VALUE = 4;

    @Test
    void executeWithStatusContinue() {
        RandomAccessMachineCommand command = new RandomAccessMachineCommand("SET", SET_VALUE);
        ExitStatus exitStatus = command.execute(ram);

        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
        Mockito.verify(ram, times(1)).set(SET_VALUE);
    }

    @Test
    void executeWithStatusInput() {
        RandomAccessMachineCommand command = new RandomAccessMachineCommand("INP", INP_VALUE);
        ExitStatus exitStatus = command.execute(ram);

        assertEquals(exitStatus.getStatus(), Status.INPUT);
        Mockito.verify(ram, times(1)).inp(INP_VALUE);
    }

    @Test
    void executeWithStatusOutput() {
        RandomAccessMachineCommand command = new RandomAccessMachineCommand("OUT", OUT_VALUE);
        ExitStatus exitStatus = command.execute(ram);

        assertEquals(exitStatus.getStatus(), Status.OUTPUT);
        Mockito.verify(ram, times(1)).out(OUT_VALUE);
    }

    @Test
    void executeWithStatusQuit() {
        RandomAccessMachineCommand command = new RandomAccessMachineCommand("HLT", HLT_VALUE);
        ExitStatus exitStatus = command.execute(ram);

        assertEquals(exitStatus.getStatus(), Status.QUIT);
        Mockito.verify(ram, times(1)).hlt(HLT_VALUE);
    }
}
