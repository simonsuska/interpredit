package de.example.domain.entities.exit;

import de.example.domain.entities.exit.details.Detail;
import de.example.domain.entities.exit.status.Status;

public class ExitStatus {
    private Status status;
    private Detail detail;

    private ExitStatus() {}

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        // TODO: Implement
        return null;
    }
}

