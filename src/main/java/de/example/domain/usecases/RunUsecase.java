package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.ExecutionTimekeeping;
import de.example.core.di.Di;
import de.example.domain.entities.Status;
import de.example.domain.entities.machines.Machine;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

/**
 * This type executes the program on the underlying machine
 * after the user has tapped the menu item.
 */
public class RunUsecase implements Runnable {

    //: SECTION: - ATTRIBUTES

    /**
     * This attribute contains the content of the editor. One element is equal
     * to one line of the editor text field.
     */
    private final List<String> program;
    private final Machine machine;

    /** This attribute is used to exchange the current status with the {@code PrinterThread}. */
    private final Exchanger<Status> exchanger;

    /**
     * This attribute is used to prevent the {@code RunnerThread} (this class) from
     * being too fast compared to the {@code PrinterThread} when writing the buffer.
     */
    private final CyclicBarrier cyclicBarrier;

    /** This attribute is used to inform the {@code FinisherThread} that the program has ended. */
    private final CyclicBarrier stopSignal;

    //: SECTION: - CONSTRUCTORS

    @Inject
    public RunUsecase(@Named(Di.MACHINE) Machine machine,
                      @Named(Di.RUN_EXCHANGER) Exchanger<Status> exchanger,
                      @Named(Di.RUN_CYCLIC_BARRIER) CyclicBarrier cyclicBarrier,
                      @Named(Di.QUIT_CYCLIC_BARRIER) CyclicBarrier stopSignal) {
        this.program = new ArrayList<>();
        this.machine = Objects.requireNonNull(machine);
        this.exchanger = Objects.requireNonNull(exchanger);
        this.cyclicBarrier = Objects.requireNonNull(cyclicBarrier);
        this.stopSignal = Objects.requireNonNull(stopSignal);
    }

    //: SECTION: - METHODS

    /**
     * This method sets the program by overwriting the previous one.
     *
     * <br><br><b>Discussion</b><br>
     * In the context of Interpredit, this method is called after the user tapped
     * the menu item and right before the {@code RunnerThread} is started.
     * @param program The program
     */
    public void setProgram(String program) {
        this.program.clear();
        this.program.addAll(Arrays.asList(program.split("\n")));
    }

    /** This method executes the program on the underlying machine. */
    @Override
    public void run() {
        ExecutionTimekeeping.start();

        int pc;
        Status status = Status.FINISH_SUCCESS;

        try {
            // This loop iterates over the whole program. In each
            // iteration, one line of code is executed.
            do {
                // If the machine has been interrupted, which only happens if the user has
                // tapped the corresponding menu item, transmit the final status and return.
                if (this.machine.isInterrupt()) {
                    try {
                        // Inform the `PrinterThread` about the interruption which will
                        // cause the `PrinterThread` to return and print an appropriate message.
                        exchanger.exchange(Status.FINISH_FAILURE);
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    // Inform the `FinisherThread` about the interruption which will
                    // cause the `FinisherThread` to enable/disable the appropriate
                    // menu items and return
                    stopSignal.await();

                    // Prepare the machine for the next program execution.
                    machine.reset();

                    return;
                }

                // If the program execution has not been interrupted by the user,
                // execute the next line of code.

                pc = this.machine.getPc();
                String cmd;

                // If the program counter is out of program, the loop starts
                // to iterate infinitely until the user interrupts the execution.
                // This happens if the program does not have an HLT command at the end.
                if (pc >= 0 && pc < program.size()) {
                    cmd = program.get(pc);
                    status = machine.run(cmd);
                } else
                    continue;

                // This condition is met if the program either requires an
                // input or output, or it has to stop due to an error.
                if (status != Status.OK) {
                    if (status == Status.FINISH_SUCCESS)
                        ExecutionTimekeeping.end();

                    // Transmit the status to the `PrinterThread` to print a
                    // message to the user.
                    exchanger.exchange(status);

                    // Prevent the `RunnerThread` (this class) from being
                    // too fast in writing the buffer
                    cyclicBarrier.await();
                }
            } while (status == Status.OK ||
                     status == Status.OUTPUT ||
                     status == Status.INPUT);

            // Inform the `FinisherThread` about the termination of the program,
            // which will cause the `FinisherThread` to enable/disable the
            // appropriate menu items and return. The termination does not
            // necessarily have to be successful.
            stopSignal.await();

            // Prepare the machine for the next program execution.
            machine.reset();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
