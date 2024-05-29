package de.example.domain.usecases;

import de.example.domain.entities.exit.builder.ExitStatus;
import de.example.domain.entities.exit.status.Status;
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

        ExitStatus exitstatus = deleteUsecase.get();
        verify(repository, times(1)).delete();
        assertEquals(exitstatus.getStatus(), Status.CONTINUE);
    }

    @Test
    void getFailure() {
        when(repository.delete()).thenReturn(false);

        ExitStatus exitstatus = deleteUsecase.get();
        verify(repository, times(1)).delete();
        assertEquals(exitstatus.getStatus(), Status.QUIT);
    }
}