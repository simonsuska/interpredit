package de.example.data.repository;

import de.example.data.datasources.MutableDatasource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryImplTest {
    private MutableDatasource mutableDatasource;
    private RepositoryImpl repository;

    private static final String OPEN_FILE_CONTENTS = "70cwkfbc1sbZ";
    private static final String SAVE_FILE_CONTENTS = "GHJE1TPP6dIy";
    private static final String FILENAME = "eBmrl0JVUU0j";

    @BeforeEach
    void setUp() {
        mutableDatasource = mock(MutableDatasource.class);
        repository = new RepositoryImpl(mutableDatasource);
    }

    @Test
    void openSuccess() {
        when(mutableDatasource.set(FILENAME)).thenReturn(true);
        when(mutableDatasource.read()).thenReturn(OPEN_FILE_CONTENTS);

        String fileContents = repository.open(FILENAME);

        verify(mutableDatasource, times(1)).set(FILENAME);
        verify(mutableDatasource, times(1)).read();
        assertEquals(fileContents, OPEN_FILE_CONTENTS);
    }

    @Test
    void openFailureSet() {
        when(mutableDatasource.set(FILENAME)).thenReturn(false);

        String fileContents = repository.open(FILENAME);
        assertNull(fileContents);
    }

    @Test
    void openFailureRead() {
        when(mutableDatasource.set(FILENAME)).thenReturn(true);
        when(mutableDatasource.read()).thenReturn(null);

        String fileContents = repository.open(FILENAME);
        assertNull(fileContents);
    }

    @Test
    void closeSuccess() {
        when(mutableDatasource.unset()).thenReturn(true);

        boolean result = repository.close();
        verify(mutableDatasource, times(1)).unset();
        assertTrue(result);
    }

    @Test
    void closeFailure() {
        when(mutableDatasource.unset()).thenReturn(false);

        boolean result = repository.close();
        assertFalse(result);
    }

    @Test
    void saveSuccess() {
        when(mutableDatasource.write(SAVE_FILE_CONTENTS)).thenReturn(true);

        boolean result = repository.save(SAVE_FILE_CONTENTS);
        verify(mutableDatasource, times(1)).write(SAVE_FILE_CONTENTS);
        assertTrue(result);
    }

    @Test
    void saveFailure() {
        when(mutableDatasource.write(SAVE_FILE_CONTENTS)).thenReturn(false);

        boolean result = repository.save(SAVE_FILE_CONTENTS);
        assertFalse(result);
    }

    @Test
    void deleteSuccess() {
        when(mutableDatasource.delete()).thenReturn(true);

        boolean result = repository.delete();
        verify(mutableDatasource, times(1)).delete();
        assertTrue(result);
    }

    @Test
    void deleteFailure() {
        when(mutableDatasource.delete()).thenReturn(false);

        boolean result = repository.delete();
        assertFalse(result);
    }
}
