package de.example.domain.entities.machines.ram;

import de.example.domain.entities.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RandomAccessMachineCommandTest {
    private RandomAccessMachine ram;

    private static final int SET_VALUE = 1;
    private static final int HOP_VALUE = 2;

    @BeforeEach
    void setUp() {
        ram = mock(RandomAccessMachine.class);
    }

    @Test
    void execute() {
        when(ram.set(SET_VALUE)).thenReturn(Status.OK);

        RandomAccessMachineCommand command = new RandomAccessMachineCommand("SET", SET_VALUE);
        Status status = command.execute(ram);

        verify(ram, times(1)).set(SET_VALUE);
        assertEquals(status, Status.OK);
    }

    @Test
    void executeUserGeneratedHop() {
        RandomAccessMachineCommand command = new RandomAccessMachineCommand("HOP", HOP_VALUE);
        Status status = command.execute(ram);

        verify(ram, times(0)).hop(HOP_VALUE);
        assertEquals(status, Status.COMMAND_ERROR);
    }

    @Test
    void executeMachineGeneratedHop() {
        when(ram.hop(HOP_VALUE)).thenReturn(Status.OK);

        RandomAccessMachineCommand command = new RandomAccessMachineCommand("HOP", HOP_VALUE, false);
        Status status = command.execute(ram);

        verify(ram, times(1)).hop(HOP_VALUE);
        assertEquals(status, Status.OK);
    }
}
