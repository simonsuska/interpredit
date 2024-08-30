package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.entities.machines.Machine;
import java.util.Objects;
import java.util.function.Supplier;

/** This type requests the output from the machine. */
public class OutputUsecase implements Supplier<String> {

    //: SECTION: - ATTRIBUTES

    private final Machine machine;

    //: SECTION: - CONSTRUCTORS

    /** This constructor initializes this use case with the appropriate machine. */
    @Inject
    public OutputUsecase(@Named(Di.MACHINE) Machine machine) {
        this.machine = Objects.requireNonNull(machine);
    }

    //: SECTION: - METHODS

    /**
     * This method requests the output from the machine.
     *
     * <br><br><b>Discussion</b><br>
     * Note that this method is blocking, which means it blocks the corresponding thread as long as the underlying
     * machine does not provide an output.
     * <br><br>
     * In the context of Interpredit, the blocking mechanism is used by the printer thread to wait until the runner
     * thread writes the buffer.
     *
     * @return The requested output from the machine
     */
    @Override
    public String get() {
        return this.machine.requestOutput();
    }
}
