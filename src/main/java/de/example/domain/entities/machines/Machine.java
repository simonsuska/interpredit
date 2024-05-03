package de.example.domain.entities.machines;

import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.FileExtension;

public abstract class Machine {
    private boolean interrupted = false;

    public static Machine get(FileExtension fileExtension) {
        // TODO: Implement
        return null;
    }

    public void interrupt() {
        this.interrupted = true;
    }

    public boolean isInterrupt() {
        return this.interrupted;
    }

    public abstract ExitStatus run(String cmd);
    public abstract String write();
    public abstract ExitStatus read(String input);
}
