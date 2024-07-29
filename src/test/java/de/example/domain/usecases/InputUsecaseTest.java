package de.example.domain.usecases;

import de.example.domain.entities.machines.Machine;
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
        machine = mock(Machine.class);
        inputUsecase = new InputUsecase(machine);
    }

    @Test
    void acceptSuccess() {
        when(machine.deliverInput(INPUT)).thenReturn(true);

        boolean result = inputUsecase.apply(INPUT);

        verify(machine, times(1)).deliverInput(INPUT);
        assertTrue(result);
    }

    @Test
    void acceptFailure() {
        when(machine.deliverInput(INPUT)).thenReturn(false);

        boolean result = inputUsecase.apply(INPUT);

        verify(machine, times(1)).deliverInput(INPUT);
        assertFalse(result);
    }
}
