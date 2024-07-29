package de.example.domain.usecases;

import de.example.domain.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUsecaseTest {
    private Repository repository;
    private DeleteUsecase deleteUsecase;

    @BeforeEach
    void setUp() {
        repository = mock(Repository.class);
        deleteUsecase = new DeleteUsecase(repository);
    }

    @Test
    void getSuccess() {
        when(repository.delete()).thenReturn(true);

        boolean result = deleteUsecase.get();
        verify(repository, times(1)).delete();
        assertTrue(result);
    }

    @Test
    void getFailure() {
        when(repository.delete()).thenReturn(false);

        boolean result = deleteUsecase.get();
        verify(repository, times(1)).delete();
        assertFalse(result);
    }
}