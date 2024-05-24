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
    void open() {
        when(fileDatasource.read()).thenReturn(OPEN_FILE_CONTENTS);

        String fileContents = repository.open(FILENAME);

        verify(fileDatasource, times(1)).set(FILENAME);
        verify(fileDatasource, times(1)).read();
        assertEquals(fileContents, OPEN_FILE_CONTENTS);
    }

    @Test
    void save() {
        repository.save(SAVE_FILE_CONTENTS);
        verify(fileDatasource, times(1)).write(SAVE_FILE_CONTENTS);
    }

    @Test
    void delete() {
        repository.delete();
        verify(fileDatasource, times(1)).unset();
    }
}
