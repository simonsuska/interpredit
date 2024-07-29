package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.entities.Status;
import de.example.domain.entities.machines.Machine;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

public class RunUsecase implements Runnable {
    private final List<String> program;
    private final Machine machine;
    private final Exchanger<Status> exchanger;
    private final CyclicBarrier cyclicBarrier;

    @Inject
    public RunUsecase(@Named(Di.MACHINE) Machine machine,
                      @Named(Di.RUN_EXCHANGER) Exchanger<Status> exchanger,
                      @Named(Di.RUN_CYCLIC_BARRIER) CyclicBarrier cyclicBarrier) {
        this.program = new ArrayList<>();
        this.machine = Objects.requireNonNull(machine);
        this.exchanger = Objects.requireNonNull(exchanger);
        this.cyclicBarrier = Objects.requireNonNull(cyclicBarrier);
    }

    public void setProgram(String program) {
        this.program.clear();
        this.program.addAll(Arrays.asList(program.split("\n")));
    }

    @Override
    public void run() {
        int pc;
        Status status;

        try {
            do {
                if (this.machine.isInterrupt()) {
                    try {
                        exchanger.exchange(Status.FINISH);
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                pc = this.machine.getPc();
                String cmd = program.get(pc);
                status = machine.run(cmd);

                if (status != Status.OK) {
                    exchanger.exchange(status);
                    cyclicBarrier.await(); // prevents the RunnerThread from being too fast in writing the buffer
                }
            } while (status == Status.OK ||
                     status == Status.OUTPUT ||
                     status == Status.INPUT);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        machine.reset();
    }
}
