package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.Di;
import de.example.domain.entities.machines.Machine;

import java.util.Objects;
import java.util.function.Supplier;

public class OutputUsecase implements Supplier<String> {
    private Machine machine;

    @Inject
    public OutputUsecase(@Named(Di.MACHINE) Machine machine) {
        this.machine = Objects.requireNonNull(machine);
    }

    @Override
    public String get() {
        // TODO: Implement
        return null;
    }
}
