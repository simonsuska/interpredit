package de.example.domain.usecases;

import de.example.domain.entities.exit.builder.ExitStatus;
import de.example.domain.entities.exit.status.Status;
import de.example.domain.entities.machines.Machine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
