package de.example.domain.entities.machines.ram;

import de.example.domain.entities.exit.builder.ExitStatus;
import de.example.domain.entities.exit.status.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RandomAccessMachineCommandTest {
    private RandomAccessMachine ram;

    private static final int SET_VALUE = 1;
    private static final int INP_VALUE = 2;
    private static final int OUT_VALUE = 3;
    private static final int HLT_VALUE = 4;

    @BeforeEach
    void setUp() {
        ram = mock(RandomAccessMachine.class);
    }

    @Test
    void executeWithStatusContinue() {
        RandomAccessMachineCommand command = new RandomAccessMachineCommand("SET", SET_VALUE);
        ExitStatus exitStatus = command.execute(ram);

        verify(ram, times(1)).set(SET_VALUE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
    }

    @Test
    void executeWithStatusInput() {
        RandomAccessMachineCommand command = new RandomAccessMachineCommand("INP", INP_VALUE);
        ExitStatus exitStatus = command.execute(ram);

        verify(ram, times(1)).inp(INP_VALUE);
        assertEquals(exitStatus.getStatus(), Status.INPUT);
    }

    @Test
    void executeWithStatusOutput() {
        RandomAccessMachineCommand command = new RandomAccessMachineCommand("OUT", OUT_VALUE);
        ExitStatus exitStatus = command.execute(ram);

        verify(ram, times(1)).out(OUT_VALUE);
        assertEquals(exitStatus.getStatus(), Status.OUTPUT);
    }

    @Test
    void executeWithStatusQuit() {
        RandomAccessMachineCommand command = new RandomAccessMachineCommand("HLT", HLT_VALUE);
        ExitStatus exitStatus = command.execute(ram);

        verify(ram, times(1)).hlt(HLT_VALUE);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }
}
