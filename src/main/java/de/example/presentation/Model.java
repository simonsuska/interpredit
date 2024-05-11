package de.example.presentation;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.Di;
import de.example.domain.usecases.*;

import java.util.Objects;

public class Model {
    private final DeleteUsecase deleteUsecase;
    private final OpenUsecase openUsecase;
    private final RunUsecase runUsecase;
    private final SaveUsecase saveUsecase;
    private final StopUsecase stopUsecase;

    @Inject
    public Model(@Named(Di.DELETE_USECASE) DeleteUsecase deleteUsecase,
                 @Named(Di.OPEN_USECASE) OpenUsecase openUsecase,
                 @Named(Di.RUN_USECASE) RunUsecase runUsecase,
                 @Named(Di.SAVE_USECASE) SaveUsecase saveUsecase,
                 @Named(Di.STOP_USECASE) StopUsecase stopUsecase) {
        this.deleteUsecase = Objects.requireNonNull(deleteUsecase);
        this.openUsecase = Objects.requireNonNull(openUsecase);
        this.runUsecase = Objects.requireNonNull(runUsecase);
        this.saveUsecase = Objects.requireNonNull(saveUsecase);
        this.stopUsecase = Objects.requireNonNull(stopUsecase);
    }

    public void delete() {
        // TODO: Implement
    }

    public void openFile(String filename) {
        // TODO: Implement
    }

    public void run() {
        // TODO: Implement
    }

    public void save(String content) {
        // TODO: Implement
    }

    public void stop() {
        // TODO: Implement
    }
}
