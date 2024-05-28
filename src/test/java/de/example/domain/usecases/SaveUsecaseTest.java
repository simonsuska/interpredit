package de.example.domain.usecases;

import de.example.data.repository.RepositoryImpl;
import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.exit.status.Status;
import de.example.domain.repository.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SaveUsecaseTest {
    private Repository repository;
    private SaveUsecase saveUsecase;

    private static final String FILE_CONTENTS = "File Contents";

    @BeforeEach
    void setUp() {
        repository = mock(Repository.class);
        saveUsecase = new SaveUsecase(repository);
    }

    @Test
    void applySuccess() {
        when(repository.save(FILE_CONTENTS)).thenReturn(true);

        ExitStatus exitStatus = saveUsecase.apply(FILE_CONTENTS);
        verify(repository, times(1)).save(FILE_CONTENTS);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
    }

    @Test
    void applyFailure() {
        when(repository.save(FILE_CONTENTS)).thenReturn(false);

        ExitStatus exitStatus = saveUsecase.apply(FILE_CONTENTS);
        verify(repository, times(1)).save(FILE_CONTENTS);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }
}