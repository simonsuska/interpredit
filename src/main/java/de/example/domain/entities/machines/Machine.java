package de.example.domain.entities.machines;

import de.example.domain.entities.Status;

public abstract class Machine {
    private boolean interrupted = false;

    public synchronized void interrupt() {
        this.interrupted = true;
    }

    public synchronized boolean isInterrupt() {
        return this.interrupted;
    }

    public abstract int getPc();
    public abstract Status run(String cmd);

    public synchronized void reset() {
        interrupted = false;
    }

    public abstract String requestOutput();
    public abstract boolean deliverInput(String input);
}
