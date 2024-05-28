package de.example.domain.usecases;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.exit.builder.ExitStatusBuilder;
import de.example.domain.entities.exit.status.Status;
import de.example.domain.entities.machines.Machine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RunUsecaseTest {
    private Machine machine;
    private RunUsecase runUsecase;

    @BeforeEach
    void setUp() {
        machine = mock(Machine.class);
        runUsecase = new RunUsecase(machine);
    }

    @Test
    void callWithStatusContinue() {
        ExitStatus e = ExitStatusBuilder.newBuilder().setStatus(Status.CONTINUE).build();
        when(machine.run(anyString())).thenReturn(e);

        ExitStatus exitStatus = runUsecase.call();

        verify(machine, times(1)).run(anyString());
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
    }

    @Test
    void callWithStatusOutput() {
        ExitStatus e = ExitStatusBuilder.newBuilder().setStatus(Status.OUTPUT).build();
        when(machine.run(anyString())).thenReturn(e);

        ExitStatus exitStatus = runUsecase.call();

        verify(machine, times(1)).run(anyString());
        assertEquals(exitStatus.getStatus(), Status.OUTPUT);
    }

    @Test
    void callWithStatusInput() {
        ExitStatus e = ExitStatusBuilder.newBuilder().setStatus(Status.INPUT).build();
        when(machine.run(anyString())).thenReturn(e);

        ExitStatus exitStatus = runUsecase.call();

        verify(machine, times(1)).run(anyString());
        assertEquals(exitStatus.getStatus(), Status.INPUT);
    }

    @Test
    void callWithStatusQuit() {
        ExitStatus e = ExitStatusBuilder.newBuilder().setStatus(Status.QUIT).build();
        when(machine.run(anyString())).thenReturn(e);

        ExitStatus exitStatus = runUsecase.call();

        verify(machine, times(1)).run(anyString());
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }
}
