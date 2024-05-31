package de.example.domain.entities.machines;

import de.example.domain.entities.exit.builder.ExitStatus;

public abstract class Machine {
    private boolean interrupted = false;

    public void interrupt() {
        this.interrupted = true;
    }

    public boolean isInterrupt() {
        return this.interrupted;
    }

    public abstract ExitStatus run(String cmd);
    public abstract String requestOutput();
    public abstract ExitStatus deliverInput(String input);
}
