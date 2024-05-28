package de.example.presentation;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.Di;
import de.example.domain.usecases.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Model {
    private final DeleteUsecase deleteUsecase;
    private final OpenUsecase openUsecase;
    private final RunUsecase runUsecase;
    private final SaveUsecase saveUsecase;
    private final StopUsecase stopUsecase;
    private final OutputUsecase outputUsecase;
    private final InputUsecase inputUsecase;

    private StringProperty outputTextAreaText;
    private StringProperty openFilename;

    @Inject
    public Model(@Named(Di.DELETE_USECASE) DeleteUsecase deleteUsecase,
                 @Named(Di.OPEN_USECASE) OpenUsecase openUsecase,
                 @Named(Di.RUN_USECASE) RunUsecase runUsecase,
                 @Named(Di.SAVE_USECASE) SaveUsecase saveUsecase,
                 @Named(Di.STOP_USECASE) StopUsecase stopUsecase,
                 @Named(Di.OUTPUT_USECASE) OutputUsecase outputUsecase,
                 @Named(Di.INPUT_USECASE) InputUsecase inputUsecase) {
        this.deleteUsecase = Objects.requireNonNull(deleteUsecase);
        this.openUsecase = Objects.requireNonNull(openUsecase);
        this.runUsecase = Objects.requireNonNull(runUsecase);
        this.saveUsecase = Objects.requireNonNull(saveUsecase);
        this.stopUsecase = Objects.requireNonNull(stopUsecase);
        this.outputUsecase = Objects.requireNonNull(outputUsecase);
        this.inputUsecase = Objects.requireNonNull(inputUsecase);

        this.outputTextAreaText = new SimpleStringProperty();
        this.openFilename = new SimpleStringProperty();
    }

    public StringProperty outputTextAreaTextProperty() {
        return outputTextAreaText;
    }

    public StringProperty openFilenameProperty() {
        return openFilename;
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

    public void requestOutput() {
        // TODO: Implement
    }

    public void deliverInput(String input) {
        // TODO: Implement
    }
}
