package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.entities.Status;
import de.example.domain.entities.machines.Machine;

import java.util.*;
import java.util.concurrent.Exchanger;

public class RunUsecase implements Runnable {
    private final List<String> program;
    private final Machine machine;
    private final Exchanger<Status> exchanger;

    @Inject
    public RunUsecase(@Named(Di.MACHINE) Machine machine,
                      @Named(Di.RUN_EXCHANGER) Exchanger<Status> exchanger) {
        this.program = new ArrayList<>();
        this.machine = Objects.requireNonNull(machine);
        this.exchanger = Objects.requireNonNull(exchanger);
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
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                // Idee: Neuer Status: Status.BRANCH. In diesem Fall wurde zuvor durch den Jump-Befehl
                // die neue Adresse des PC in den Buffer geschrieben. Der RunUseCase liest diesen Buffer dann
                // anschließend aus und greift dann auf diesen Index in der Program-List zu. Falls der Index
                // out of bounds ist, wird Status.QUIT (oder eine neuer Status Status.INVALID_JUMP oder so) zurückgegeben
                // WICHTIG: Die Tests müssen dann natürlich auch angepasst werden!!!

                // Allgemeiner: Nicht immer automatisch das erste Element nehmen, sondern
                // auf den PC der Machine zugreifen. Entweder durch eine neue Methode getPC()
                // oder wie oben beschrieben über den Buffer.
                pc = this.machine.getPc();
                String cmd = program.get(pc);
                status = machine.run(cmd);

                if (status != Status.OK) {
                    exchanger.exchange(status);
                }
            } while (status == Status.OK ||
                     status == Status.OUTPUT ||
                     status == Status.INPUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        machine.reset();
    }
}
