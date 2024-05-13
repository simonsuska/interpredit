package de.example.data.repository;

import de.example.data.datasources.FileDatasource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class RepositoryImplTest {
    @Mock
    private FileDatasource fileDatasource;

    @InjectMocks
    private RepositoryImpl repository;

    private static final String OPEN_FILE_CONTENTS = "Open file contents";
    private static final String SAVE_FILE_CONTENTS = "Save file contents";
    private static final String FILENAME = "Filename";

    @Test
    void open() {
        Mockito.when(fileDatasource.read()).thenReturn(OPEN_FILE_CONTENTS);

        String fileContents = repository.open(FILENAME);

        Mockito.verify(fileDatasource, times(1)).set(FILENAME);
        Mockito.verify(fileDatasource, times(1)).read();
        assertEquals(fileContents, OPEN_FILE_CONTENTS);
    }

    @Test
    void save() {
        repository.save(SAVE_FILE_CONTENTS);
        Mockito.verify(fileDatasource, times(1)).write(SAVE_FILE_CONTENTS);
    }

    @Test
    void delete() {
        repository.delete();
        Mockito.verify(fileDatasource, times(1)).unset();
    }
}
