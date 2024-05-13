package de.example.domain.entities.exit.builder;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.exit.details.Interrupted;
import de.example.domain.entities.exit.status.Status;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ExitStatusBuilderTest {
    @Mock
    private Interrupted interrupted;

    private static final String INTERRUPTED_DESCRIPTION = "Interrupted description";

    @Test
    void build() {
        ExitStatus exitStatus = ExitStatusBuilder.newBuilder().build();

        assertEquals(exitStatus.getStatus(), Status.OK);
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
        Mockito.when(interrupted.getDescription()).thenReturn(INTERRUPTED_DESCRIPTION);

        ExitStatus exitStatus = ExitStatusBuilder.newBuilder()
                .setDetail(new Interrupted())
                .build();

        assertEquals(exitStatus.getStatus(), Status.OK);
        assertEquals(exitStatus.getDescription(), INTERRUPTED_DESCRIPTION);
    }
}
