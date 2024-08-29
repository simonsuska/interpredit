package de.example.domain.entities.machines;

import de.example.domain.entities.Status;

/** A type that defines the basic functions of a machine. */
public abstract class Machine {

    //: SECTION: - ATTRIBUTES

    private boolean interrupted = false;

    //: SECTION: - METHODS

    /** This method interrupts the machine. */
    public synchronized void interrupt() {
        this.interrupted = true;
    }

    /**
     * This method checks whether the machine is interrupted or not.
     * @return {@code true} if the machine is interrupted, otherwise {@code false}
     */
    public synchronized boolean isInterrupt() {
        return this.interrupted;
    }

    public abstract int getPc();
    public abstract Status run(String cmd);

    /** This method resets the machine, which is restoring the uninterrupted state. */
    public synchronized void reset() {
        interrupted = false;
    }

    public abstract String requestOutput();
    public abstract boolean deliverInput(String input);
}
