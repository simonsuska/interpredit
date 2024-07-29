package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.entities.machines.Machine;

import java.util.Objects;
import java.util.function.Function;

public class InputUsecase implements Function<String, Boolean> {
    private final Machine machine;

    @Inject
    public InputUsecase(@Named(Di.MACHINE) Machine machine) {
        this.machine = Objects.requireNonNull(machine);
    }

    @Override
    public Boolean apply(String s) {
        return this.machine.deliverInput(s);
    }
}
