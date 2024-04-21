package de.example.domain.entities.machines;

import de.example.core.exceptions.InputException;
import de.example.core.exceptions.InterpreditException;
import de.example.domain.entities.ExitStatus;
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

    public abstract ExitStatus run(String program) throws InterpreditException;
    public abstract String write();
    public abstract void read(String input) throws InputException;
}
