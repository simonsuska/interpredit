package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.Di;
import de.example.domain.entities.exit.builder.ExitStatus;
import de.example.domain.entities.machines.Machine;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.concurrent.Callable;

public class RunUsecase implements Callable<ExitStatus> {
    private ArrayDeque<String> program;
    private Machine machine;

    @Inject
    public RunUsecase(@Named(Di.MACHINE) Machine machine) {
        this.machine = Objects.requireNonNull(machine);
        this.program = new ArrayDeque<>();
    }

    public void setProgram(String program) {
        // TODO: Implement
    }

    @Override
    public ExitStatus call() {
        // TODO: Implement
        return null;
    }
}
