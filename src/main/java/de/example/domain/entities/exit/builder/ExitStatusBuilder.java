package de.example.domain.entities.exit.builder;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.exit.details.Detail;
import de.example.domain.entities.exit.status.Status;

public interface ExitStatusBuilder {
    ExitStatusBuilder newBuilder();
    ExitStatusBuilder setStatus(Status status);
    ExitStatusBuilder setDetail(Detail detail);
    ExitStatus build();
}
