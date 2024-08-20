package de.example.domain.usecases;

import de.example.domain.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenUsecaseTest {
    private Repository repository;
    private OpenUsecase openUsecase;

    private static final String FILENAME = "s5IshG0IFVrG";
    private static final String FILE_CONTENTS = "CZAo0dckglp8";

    @BeforeEach
    void setUp() {
        repository = mock(Repository.class);
        openUsecase = new OpenUsecase(repository);
    }

    @Test
    void applySuccess() {
        when(repository.open(FILENAME)).thenReturn(FILE_CONTENTS);

        String content = openUsecase.apply(FILENAME);
        verify(repository, times(1)).open(FILENAME);
        assertEquals(content, FILE_CONTENTS);
    }

    @Test
    void applyFailure() {
        when(repository.open(FILENAME)).thenReturn(null);

        String content = openUsecase.apply(FILENAME);
        verify(repository, times(1)).open(FILENAME);
        assertNull(content);
    }
}