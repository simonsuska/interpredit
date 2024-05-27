package de.example.domain.usecases;

import de.example.domain.entities.machines.Machine;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OutputUsecaseTest {
    @Mock
    private Machine machine;

    @InjectMocks
    private OutputUsecase outputUsecase;

    private static final String OUTPUT = "Output";

    @Test
    void get() {
        when(machine.requestOutput()).thenReturn(OUTPUT);

        String result = outputUsecase.get();

        verify(machine, times(1)).requestOutput();
        assertEquals(result, OUTPUT);
    }
}
