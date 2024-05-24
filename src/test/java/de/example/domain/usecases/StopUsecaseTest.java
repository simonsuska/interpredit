package de.example.domain.usecases;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.exit.status.Status;
import de.example.domain.entities.machines.Machine;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StopUsecaseTest {
    @Mock
    private Machine machine;

    @InjectMocks
    private StopUsecase stopUsecase;

    @Test
    void get() {
        ExitStatus exitStatus = stopUsecase.get();
        verify(machine, times(1)).interrupt();
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }
}
