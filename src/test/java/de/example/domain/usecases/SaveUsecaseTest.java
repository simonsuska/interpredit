package de.example.domain.usecases;

import de.example.domain.repository.Repository;
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

        boolean result = saveUsecase.apply(FILE_CONTENTS);
        verify(repository, times(1)).save(FILE_CONTENTS);
        assertTrue(result);
    }

    @Test
    void applyFailure() {
        when(repository.save(FILE_CONTENTS)).thenReturn(false);

        boolean result = saveUsecase.apply(FILE_CONTENTS);
        verify(repository, times(1)).save(FILE_CONTENTS);
        assertFalse(result);
    }
}