package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.entities.machines.Machine;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * This type interrupts the machine which will cause the program execution to stop after the user has clicked on the
 * stop menu item.
 */
public class StopUsecase implements Supplier<Boolean> {

    //: SECTION: - ATTRIBUTES

    private final Machine machine;

    //: SECTION: - CONSTRUCTORS

    /** This constructor initializes this use case with the appropriate machine. */
    @Inject
    public StopUsecase(@Named(Di.MACHINE) Machine machine) {
        this.machine = Objects.requireNonNull(machine);
    }

    //: SECTION: - METHODS

    /**
     * This method interrupts the machine which will cause the program execution to stop.
     *
     * @return Always {@code true}
     */
    @Override
    public Boolean get() {
        this.machine.interrupt();
        return true;
    }
}
