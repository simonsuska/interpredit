package de.example.domain.usecases;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.exit.builder.ExitStatusBuilder;
import de.example.domain.entities.exit.status.Status;
import de.example.domain.entities.machines.Machine;
import de.example.domain.entities.machines.ram.RandomAccessMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InputUsecaseTest {
    private Machine machine;
    private InputUsecase inputUsecase;

    private static final String INPUT = "Input";

    @BeforeEach
    void setUp() {
        machine = mock(RandomAccessMachine.class);
        inputUsecase = new InputUsecase(machine);
    }

    @Test
    void acceptSuccess() {
        ExitStatus e = ExitStatusBuilder.newBuilder().setStatus(Status.CONTINUE).build();
        when(machine.deliverInput(INPUT)).thenReturn(e);

        ExitStatus exitStatus = inputUsecase.apply(INPUT);

        verify(machine, times(1)).deliverInput(INPUT);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
    }

    @Test
    void acceptFailure() {
        ExitStatus e = ExitStatusBuilder.newBuilder().setStatus(Status.QUIT).build();
        when(machine.deliverInput(INPUT)).thenReturn(e);

        ExitStatus exitStatus = inputUsecase.apply(INPUT);

        verify(machine, times(1)).deliverInput(INPUT);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }
}
