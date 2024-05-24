package de.example.domain.entities.exit.builder;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.exit.details.Interrupted;
import de.example.domain.entities.exit.status.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExitStatusBuilderTest {
    private Interrupted interrupted;

    private static final String INTERRUPTED_DESCRIPTION = "Interrupted description";

    @BeforeEach
    void setUp() {
        interrupted = mock(Interrupted.class);
    }

    @Test
    void build() {
        ExitStatus exitStatus = ExitStatusBuilder.newBuilder().build();

        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
        assertEquals(exitStatus.getDescription(), "");
    }

    @Test
    void buildWithStatus() {
        ExitStatus exitStatus = ExitStatusBuilder.newBuilder()
                .setStatus(Status.QUIT)
                .build();

        assertEquals(exitStatus.getStatus(), Status.QUIT);
        assertEquals(exitStatus.getDescription(), "");
    }

    @Test
    void buildWithDescription() {
        when(interrupted.getDescription()).thenReturn(INTERRUPTED_DESCRIPTION);

        ExitStatus exitStatus = ExitStatusBuilder.newBuilder()
                .setDetail(new Interrupted())
                .build();

        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
        assertEquals(exitStatus.getDescription(), INTERRUPTED_DESCRIPTION);
    }
}
