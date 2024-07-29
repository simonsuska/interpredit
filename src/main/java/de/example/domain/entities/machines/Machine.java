package de.example.domain.entities.machines;

import de.example.domain.entities.Status;

public abstract class Machine {
    private boolean interrupted = false;

    public void interrupt() {
        this.interrupted = true;
    }

    public boolean isInterrupt() {
        return this.interrupted;
    }

    public abstract int getPc();
    public abstract Status run(String cmd);

    public abstract void reset();
    public abstract String requestOutput();
    public abstract boolean deliverInput(String input);
}
