package de.example.domain.usecases;

import de.example.data.repository.RepositoryImpl;
import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.exit.status.Status;
import de.example.domain.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenUsecaseTest {
    private Repository repository;
    private OpenUsecase openUsecase;

    private static final String FILENAME = "Filename";
    private static final String FILE_CONTENTS = "File Contents";

    @BeforeEach
    void setUp() {
        repository = mock(RepositoryImpl.class);
        openUsecase = new OpenUsecase(repository);
    }

    @Test
    void applySuccess() {
        when(repository.open(FILENAME)).thenReturn(FILE_CONTENTS);

        ExitStatus exitStatus = openUsecase.apply(FILENAME);
        verify(repository, times(1)).open(FILENAME);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
    }

    @Test
    void applyFailure() {
        when(repository.open(FILENAME)).thenReturn(null);

        ExitStatus exitStatus = openUsecase.apply(FILENAME);
        verify(repository, times(1)).open(FILENAME);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }
}