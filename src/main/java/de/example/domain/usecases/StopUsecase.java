package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.Di;
import de.example.domain.entities.exit.builder.ExitStatus;
import de.example.domain.entities.machines.Machine;

import java.util.Objects;
import java.util.function.Supplier;

public class StopUsecase implements Supplier<ExitStatus> {
    private Machine machine;

    @Inject
    public StopUsecase(@Named(Di.MACHINE) Machine machine) {
        this.machine = Objects.requireNonNull(machine);
    }

    @Override
    public ExitStatus get() {
        // TODO: Implement
        return null;
    }
}
