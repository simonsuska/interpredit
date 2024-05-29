package de.example.domain.entities.exit.builder;

import de.example.domain.entities.exit.details.Detail;
import de.example.domain.entities.exit.status.Status;

/**
 * This type represents an exit status containing information
 * about the status itself and additional details.
 *
 * In the context of Interpredit, it is used by all random access machine
 * components as well as several use cases to provide information on how
 * to proceed. The exit status is passed through in the most cases and
 * finally evaluated in the model.
 */
public class ExitStatus {
    private Status status;
    private Detail detail;

    /** This constructor creates an exit status with default values. */
    protected ExitStatus() {
        this.status = Status.CONTINUE;
        this.detail = null;
    }

    /**
     * This method overrides the current status with the given one.
     * @param status The new status to set
     */
    protected void setStatus(Status status) {
        if (status == null) this.status = Status.CONTINUE;
        else this.status = status;
    }

    /**
     * This method overrides the current detail with the given one.
     * @param detail The new detail to set
     */
    protected void setDetail(Detail detail) {
        this.detail = detail;
    }

    /**
     * This method returns the currently set status.
     * @return The currently set status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * This method returns a textual representation of the
     * currently set detail or an empty string, if no detail is set.
     * @return The textual representation of the currently set detail
     *         or an empty string, if no detail is set
     */
    public String getDescription() {
        if (detail == null)
            return "";
        return detail.getDescription();
    }
}
