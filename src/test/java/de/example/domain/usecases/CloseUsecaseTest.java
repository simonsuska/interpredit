package de.example.domain.usecases;

import de.example.domain.entities.exit.builder.ExitStatus;
import de.example.domain.entities.exit.status.Status;
import de.example.domain.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CloseUsecaseTest {
    private Repository repository;
    private CloseUsecase closeUsecase;

    @BeforeEach
    void setUp() {
        repository = mock(Repository.class);
        closeUsecase = new CloseUsecase(repository);
    }

    @Test
    void getSuccess() {
        when(repository.close()).thenReturn(true);

        ExitStatus exitstatus = closeUsecase.get();
        verify(repository, times(1)).close();
        assertEquals(exitstatus.getStatus(), Status.CONTINUE);
    }

    @Test
    void getFailure() {
        when(repository.close()).thenReturn(false);

        ExitStatus exitstatus = closeUsecase.get();
        verify(repository, times(1)).close();
        assertEquals(exitstatus.getStatus(), Status.QUIT);
    }
}
