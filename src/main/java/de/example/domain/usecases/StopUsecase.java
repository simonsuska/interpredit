package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.entities.machines.Machine;

import java.util.Objects;
import java.util.function.Supplier;

public class StopUsecase implements Supplier<Boolean> {
    private final Machine machine;

    @Inject
    public StopUsecase(@Named(Di.MACHINE) Machine machine) {
        this.machine = Objects.requireNonNull(machine);
    }

    @Override
    public Boolean get() {
        this.machine.interrupt();
        return true;
    }
}
