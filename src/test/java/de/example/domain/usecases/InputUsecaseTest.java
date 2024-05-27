package de.example.domain.usecases;

import de.example.domain.entities.machines.Machine;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

class InputUsecaseTest {
    @Mock
    private Machine machine;

    @InjectMocks
    private InputUsecase inputUsecase;

    private static final String INPUT = "Input";

    @Test
    void accept() {
        inputUsecase.accept(INPUT);
        verify(machine, times(1)).deliverInput(INPUT);
    }
}
