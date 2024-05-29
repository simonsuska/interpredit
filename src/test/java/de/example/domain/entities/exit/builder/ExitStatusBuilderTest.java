package de.example.domain.entities.exit.builder;

import de.example.domain.entities.exit.details.Detail;
import de.example.domain.entities.exit.status.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExitStatusBuilderTest {
    private Detail detail;
    private static final String DETAIL_DESCRIPTION = "-AW=xl:$nI";

    @BeforeEach
    void setUp() {
        detail = mock(Detail.class);
    }

    /** This test evaluates whether building a default exit status works properly. */
    @Test
    void build() {
        ExitStatus exitStatus = ExitStatusBuilder.newBuilder().build();

        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
        assertEquals(exitStatus.getDescription(), "");
    }

    /**
     * This test evaluates whether building an exit status
     * by only specifying the status works properly.
     */
    @Test
    void buildWithStatus() {
        ExitStatus exitStatus = ExitStatusBuilder.newBuilder()
                .setStatus(Status.QUIT)
                .build();

        assertEquals(exitStatus.getStatus(), Status.QUIT);
        assertEquals(exitStatus.getDescription(), "");
    }

    /**
     * This test evaluates whether building an exit status
     * by only specifying the detail works properly.
     */
    @Test
    void buildWithDescription() {
        when(detail.getDescription()).thenReturn(DETAIL_DESCRIPTION);

        ExitStatus exitStatus = ExitStatusBuilder.newBuilder()
                .setDetail(detail)
                .build();

        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
        assertEquals(exitStatus.getDescription(), DETAIL_DESCRIPTION);
    }
}
