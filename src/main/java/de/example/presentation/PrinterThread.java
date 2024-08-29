package de.example.presentation;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.ExecutionTimekeeping;
import de.example.core.di.Di;
import de.example.domain.entities.Status;
import javafx.application.Platform;

import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

import static de.example.presentation.Interpredit.s;

/** This type prints messages about the current status to the user. */
public class PrinterThread implements Runnable {

    //: SECTION: - ATTRIBUTES

    private final Model model;

    /**
     * This attribute is used to receive the current status
     * from the {@code RunnerThread} (aka {@code RunUsecase})
     */
    private final Exchanger<Status> exchanger;

    /**
     * This attribute is used to prevent the {@code RunnerThread} (this class) from
     * being too fast compared to the {@code PrinterThread} when writing the buffer.
     */
    private final CyclicBarrier cyclicBarrier;

    //: SECTION: - CONSTRUCTORS

    @Inject
    public PrinterThread(@Named(Di.MODEL) Model model,
                         @Named(Di.RUN_EXCHANGER) Exchanger<Status> exchanger,
                         @Named(Di.RUN_CYCLIC_BARRIER) CyclicBarrier cyclicBarrier) {
        this.model = Objects.requireNonNull(model);
        this.exchanger = Objects.requireNonNull(exchanger);
        this.cyclicBarrier = Objects.requireNonNull(cyclicBarrier);
    }

    //: SECTION: - METHODS

    /**
     * This method receives the current status from the {@code RunnerThread}
     * and prints an appropriate message to the user.
     */
    @Override
    public void run() {
        Runnable runnable;
        Status status = Status.OK;

        while (status == Status.OK ||
               status == Status.OUTPUT ||
               status == Status.INPUT) {
            try {
                // Wait for the next status
                status = exchanger.exchange(Status.OK);

                // Determine the message based on the status
                runnable = switch (status) {
                    case OUTPUT -> {
                        String output = this.model.requestOutput();
                        yield () -> this.model.appendOutput(output);
                    }
                    case INPUT -> () -> this.model.appendOutput(s("inputHintMessage"));
                    case SET_ERROR -> () -> this.model.appendOutput(s("setErrorHintMessage") + "\n" +
                                                                    s("finishFailureHintMessage"));
                    case MEMORY_ADDRESS_ERROR -> () -> this.model.appendOutput(s("memoryAddressErrorHintMessage") + "\n" +
                                                                               s("finishFailureHintMessage"));
                    case COMMAND_ERROR -> () -> this.model.appendOutput(s("commandErrorHintMessage") + "\n" +
                                                                        s("finishFailureHintMessage"));
                    case DECODE_ERROR -> () -> this.model.appendOutput(s("decodeErrorHintMessage") + "\n" +
                                                                       s("finishFailureHintMessage"));
                    case DIVISION_BY_ZERO_ERROR -> () -> this.model.appendOutput(s("divisionByZeroErrorHintMessage") + "\n" +
                                                                                 s("finishFailureHintMessage"));
                    case INPUT_ERROR -> () -> this.model.appendOutput(s("inputErrorHintMessage") + "\n" +
                                                                      s("finishFailureHintMessage"));
                    case FINISH_SUCCESS -> () -> this.model.appendOutput(s("finishSuccessHintMessage") + "\n" +
                                                                         s("executionDurationHintMessage",
                                                                                ExecutionTimekeeping.getDuration()));
                    case FINISH_FAILURE -> () -> this.model.appendOutput(s("finishFailureHintMessage"));
                    default -> null;
                };

                // Issue the message
                if (runnable != null)
                    Platform.runLater(runnable);

                // Prevent the `RunnerThread` from being too fast
                cyclicBarrier.await();
                cyclicBarrier.reset();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
