package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.Di;
import de.example.domain.entities.exit.builder.ExitStatus;
import de.example.domain.entities.machines.Machine;

import java.util.Objects;
import java.util.function.Function;

public class InputUsecase implements Function<String, ExitStatus> {
    private Machine machine;

    @Inject
    public InputUsecase(@Named(Di.MACHINE) Machine machine) {
        this.machine = Objects.requireNonNull(machine);
    }

    @Override
    public ExitStatus apply(String s) {
        // TODO: Implement
        return null;
    }
}
