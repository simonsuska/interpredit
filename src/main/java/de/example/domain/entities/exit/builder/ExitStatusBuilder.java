package de.example.domain.entities.exit.builder;

import de.example.domain.entities.exit.details.Detail;
import de.example.domain.entities.exit.status.Status;

/** This type builds an exit status. */
public class ExitStatusBuilder {
    private final ExitStatus exitStatus = new ExitStatus();

    /**
     * This method creates a new `ExitStatusBuilder`
     * @return A new `ExitStatusBuilder`
     */
    public static ExitStatusBuilder newBuilder() {
        return new ExitStatusBuilder();
    }

    /**
     * This method sets the status for the exit status.
     * @param status The status to set for the exit status
     * @return The `ExitStatusBuilder` itself
     */
    public ExitStatusBuilder setStatus(Status status) {
        this.exitStatus.setStatus(status);
        return this;
    }

    /**
     * This method sets the detail for the exit status.
     * @param detail The detail to set for the exit status
     * @return The `ExitStatusBuilder` itself
     */
    public ExitStatusBuilder setDetail(Detail detail) {
        this.exitStatus.setDetail(detail);
        return this;
    }

    /**
     * This method builds the exit status.
     * @return The `ExitStatus`
     */
    public ExitStatus build() {
        return this.exitStatus;
    }
}
