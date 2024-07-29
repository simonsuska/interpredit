package de.example.presentation;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.entities.Status;
import javafx.application.Platform;

import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

public class PrinterThread implements Runnable {
    private final Model model;
    private final Exchanger<Status> exchanger;
    private final CyclicBarrier cyclicBarrier;

    @Inject
    public PrinterThread(@Named(Di.MODEL) Model model,
                         @Named(Di.RUN_EXCHANGER) Exchanger<Status> exchanger,
                         @Named(Di.RUN_CYCLIC_BARRIER)CyclicBarrier cyclicBarrier) {
        this.model = Objects.requireNonNull(model);
        this.exchanger = Objects.requireNonNull(exchanger);
        this.cyclicBarrier = Objects.requireNonNull(cyclicBarrier);
    }

    @Override
    public void run() {
        Runnable runnable;
        Status status = Status.OK;

        while (status != Status.FINISH) {
            try {
                status = exchanger.exchange(Status.OK);

                runnable = switch (status) {
                    case OUTPUT -> {
                        String output = this.model.requestOutput();
                        yield () -> this.model.appendOutput(output);
                    }
                    case INPUT -> () -> this.model.appendOutput("Enter input"); // TODO: Implement output
                    case SET_ERROR -> () -> this.model.appendOutput("Set Error"); // TODO: Implement output
                    case MEMORY_ADDRESS_ERROR -> () -> this.model.appendOutput("Memory Address Error"); // TODO: Implement output
                    case COMMAND_ERROR -> () -> this.model.appendOutput("Command Error"); // TODO: Implement output
                    case DECODE_ERROR -> () -> this.model.appendOutput("Decode Error"); // TODO: Implement output
                    case FINISH -> () -> this.model.appendOutput("Quit"); // TODO: Implement output
                    default -> null;
                };

                if (runnable != null)
                    Platform.runLater(runnable);

                cyclicBarrier.await();
                cyclicBarrier.reset();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
