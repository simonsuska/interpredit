package de.example.data.repository;

import de.example.data.datasources.FileDatasource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryImplTest {
    private FileDatasource fileDatasource;
    private RepositoryImpl repository;

    private static final String OPEN_FILE_CONTENTS = "Open file contents";
    private static final String SAVE_FILE_CONTENTS = "Save file contents";
    private static final String FILENAME = "Filename";

    @BeforeEach
    void setUp() {
        fileDatasource = mock(FileDatasource.class);
        repository = new RepositoryImpl(fileDatasource);
    }

    @Test
    void openSuccess() {
        when(fileDatasource.set(FILENAME)).thenReturn(true);
        when(fileDatasource.read()).thenReturn(OPEN_FILE_CONTENTS);

        String fileContents = repository.open(FILENAME);

        verify(fileDatasource, times(1)).set(FILENAME);
        verify(fileDatasource, times(1)).read();
        assertEquals(fileContents, OPEN_FILE_CONTENTS);
    }

    @Test
    void openFailure() {
        when(fileDatasource.set(FILENAME)).thenReturn(false);
        when(fileDatasource.read()).thenReturn(null);

        String fileContents = repository.open(FILENAME);
        assertNull(fileContents);
    }

    @Test
    void saveSuccess() {
        when(fileDatasource.write(SAVE_FILE_CONTENTS)).thenReturn(true);

        boolean result = repository.save(SAVE_FILE_CONTENTS);
        verify(fileDatasource, times(1)).write(SAVE_FILE_CONTENTS);
        assertTrue(result);
    }

    @Test
    void saveFailure() {
        when(fileDatasource.write(SAVE_FILE_CONTENTS)).thenReturn(false);

        boolean result = repository.save(SAVE_FILE_CONTENTS);
        assertFalse(result);
    }

    @Test
    void deleteSuccess() {
        when(fileDatasource.unset()).thenReturn(true);

        boolean result = repository.delete();
        verify(fileDatasource, times(1)).unset();
        assertTrue(result);
    }

    @Test
    void deleteFailure() {
        when(fileDatasource.unset()).thenReturn(false);

        boolean result = repository.delete();
        assertFalse(result);
    }
}
