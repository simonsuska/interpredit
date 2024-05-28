package de.example.presentation;

import de.example.domain.usecases.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModelTest {
    @Mock DeleteUsecase deleteUsecase;
    @Mock OpenUsecase openUsecase;
    @Mock SaveUsecase saveUsecase;
    @Mock StopUsecase stopUsecase;
    @Mock OutputUsecase outputUsecase;
    @Mock InputUsecase inputUsecase;

    @InjectMocks
    private Model model;

    private static final String OPEN_FILENAME = "Open Filename";
    private static final String SAVE_FILENAME = "Save Filename";
    private static final String INPUT = "Input";

    @Test
    void delete() {
        model.delete();
        verify(deleteUsecase, times(1)).get();
    }

    @Test
    void openFile() {
        model.openFile(OPEN_FILENAME);
        verify(openUsecase, times(1)).apply(OPEN_FILENAME);
    }

    @Test
    void run() {
    }

    @Test
    void save() {
        model.save(SAVE_FILENAME);
        verify(saveUsecase, times(1)).apply(SAVE_FILENAME);
    }

    @Test
    void stop() {
        model.stop();
        verify(stopUsecase, times(1)).get();
    }

    @Test
    void requestOutput() {
        model.requestOutput();
        verify(outputUsecase, times(1)).get();
    }

    @Test
    void deliverInput() {
        model.deliverInput(INPUT);
        verify(inputUsecase, times(1)).apply(INPUT);
    }
}
