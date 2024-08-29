package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.entities.machines.Machine;

import java.util.Objects;
import java.util.function.Function;

/** This type transmits the user input to the machine. */
public class InputUsecase implements Function<String, Boolean> {

    //: SECTION: - ATTRIBUTES

    private final Machine machine;

    //: SECTION: - CONSTRUCTORS

    /** This constructor initializes this use case with the appropriate machine. */
    @Inject
    public InputUsecase(@Named(Di.MACHINE) Machine machine) {
        this.machine = Objects.requireNonNull(machine);
    }

    //: SECTION: - METHODS

    /**
     * This method transmits the user input to the machine.
     * @param s The user input
     * @return {@code true}, if {@code s} is not {@code null},
     *         otherwise {@code false}
     */
    @Override
    public Boolean apply(String s) {
        return this.machine.deliverInput(s);
    }
}
