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
    private static final int INP_VALUE = 2;
    private static final int OUT_VALUE = 3;
    private static final int HLT_VALUE = 4;

    @BeforeEach
    void setUp() {
        ram = mock(RandomAccessMachine.class);
    }

    @Test
    void executeWithStatusContinue() {
        when(ram.set(SET_VALUE)).thenReturn(Status.OK);

        RandomAccessMachineCommand command = new RandomAccessMachineCommand("SET", SET_VALUE);
        Status status = command.execute(ram);

        verify(ram, times(1)).set(SET_VALUE);
        assertEquals(status, Status.OK);
    }

    @Test
    void executeWithStatusInput() {
        when(ram.inp(INP_VALUE)).thenReturn(Status.INPUT);

        RandomAccessMachineCommand command = new RandomAccessMachineCommand("INP", INP_VALUE);
        Status status = command.execute(ram);

        verify(ram, times(1)).inp(INP_VALUE);
        assertEquals(status, Status.INPUT);
    }

    @Test
    void executeWithStatusOutput() {
        when(ram.out(OUT_VALUE)).thenReturn(Status.OUTPUT);

        RandomAccessMachineCommand command = new RandomAccessMachineCommand("OUT", OUT_VALUE);
        Status status = command.execute(ram);

        verify(ram, times(1)).out(OUT_VALUE);
        assertEquals(status, Status.OUTPUT);
    }

    @Test
    void executeWithStatusFinish() {
        when(ram.hlt(HLT_VALUE)).thenReturn(Status.FINISH);

        RandomAccessMachineCommand command = new RandomAccessMachineCommand("HLT", HLT_VALUE);
        Status status = command.execute(ram);

        verify(ram, times(1)).hlt(HLT_VALUE);
        assertEquals(status, Status.FINISH);
    }
}
