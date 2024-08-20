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
    @Mock CloseUsecase closeUsecase;
    @Mock RunUsecase runUsecase;
    @Mock SaveUsecase saveUsecase;
    @Mock StopUsecase stopUsecase;
    @Mock OutputUsecase outputUsecase;
    @Mock InputUsecase inputUsecase;

    @InjectMocks
    private Model model;

    private static final String OPEN_FILENAME = "kro4E5pLzjtR";
    private static final String RUN_PROGRAM = "pAiwXCSJxBLK";
    private static final String SAVE_FILENAME = "sni5HwrxolvC";
    private static final String INPUT = "xxbYWS3FwvTM";

    @Test
    void deleteFile() {
        model.deleteFile();
        verify(deleteUsecase, times(1)).get();
    }

    @Test
    void openFile() {
        model.openFile(OPEN_FILENAME);
        verify(openUsecase, times(1)).apply(OPEN_FILENAME);
    }

    @Test
    void closeFile() {
        model.closeFile();
        verify(closeUsecase, times(1)).get();
    }

    @Test
    void run() {
        model.run(RUN_PROGRAM);
        verify(runUsecase, times(1)).setProgram(RUN_PROGRAM);
    }

    @Test
    void saveFile() {
        model.saveFile(SAVE_FILENAME);
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
