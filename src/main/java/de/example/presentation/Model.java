package de.example.presentation;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.usecases.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {
    private final DeleteUsecase deleteUsecase;
    private final OpenUsecase openUsecase;
    private final CloseUsecase closeUsecase;
    private final RunUsecase runUsecase;
    private final SaveUsecase saveUsecase;
    private final StopUsecase stopUsecase;
    private final OutputUsecase outputUsecase;
    private final InputUsecase inputUsecase;

    private final StringProperty editorTextAreaText;
    private final StringProperty outputTextAreaText;
    private final StringProperty fileLabelText;

    @Inject
    public Model(@Named(Di.DELETE_USECASE) DeleteUsecase deleteUsecase,
                 @Named(Di.OPEN_USECASE) OpenUsecase openUsecase,
                 @Named(Di.CLOSE_USECASE) CloseUsecase closeUsecase,
                 @Named(Di.RUN_USECASE) RunUsecase runUsecase,
                 @Named(Di.SAVE_USECASE) SaveUsecase saveUsecase,
                 @Named(Di.STOP_USECASE) StopUsecase stopUsecase,
                 @Named(Di.OUTPUT_USECASE) OutputUsecase outputUsecase,
                 @Named(Di.INPUT_USECASE) InputUsecase inputUsecase) {
        this.deleteUsecase = Objects.requireNonNull(deleteUsecase);
        this.openUsecase = Objects.requireNonNull(openUsecase);
        this.closeUsecase = Objects.requireNonNull(closeUsecase);
        this.runUsecase = Objects.requireNonNull(runUsecase);
        this.saveUsecase = Objects.requireNonNull(saveUsecase);
        this.stopUsecase = Objects.requireNonNull(stopUsecase);
        this.outputUsecase = Objects.requireNonNull(outputUsecase);
        this.inputUsecase = Objects.requireNonNull(inputUsecase);

        this.editorTextAreaText = new SimpleStringProperty("");
        this.outputTextAreaText = new SimpleStringProperty("");
        this.fileLabelText = new SimpleStringProperty();
    }

    public StringProperty editorTextAreaTextProperty() {
        return editorTextAreaText;
    }

    public StringProperty outputTextAreaTextProperty() {
        return outputTextAreaText;
    }

    public StringProperty fileLabelTextProperty() {
        return fileLabelText;
    }

    public void appendOutput(String output) {
        outputTextAreaText.set(outputTextAreaText.get() + output + "\n");
    }

    public void deleteFile() {
        boolean result = this.deleteUsecase.get();

        if (result) {
            this.editorTextAreaText.set("");
            appendOutput(""); // TODO: Implement output
            this.fileLabelText.set("");
        } else {
            appendOutput(""); // TODO: Implement output
        }
    }

    public void openFile(String filename) {
        String content = this.openUsecase.apply(filename);

        if (content != null) {
            this.editorTextAreaText.set(content);
            appendOutput(""); // TODO: Implement output
            this.fileLabelText.set(filename);
        } else {
            appendOutput(""); // TODO: Implement output
        }
    }

    public void closeFile() {
        boolean result = this.closeUsecase.get();

        if (result) {
            this.editorTextAreaText.set("");
            appendOutput(""); // TODO: Implement output
            this.fileLabelText.set("");
        } else {
            appendOutput(""); // TODO: Implement output
        }
    }

    public void run(String program) {
        Executors.newSingleThreadExecutor().submit(Interpredit.getPrinterThread());
        this.runUsecase.setProgram(program);

        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(runUsecase);
    }

    public void saveFile(String content) {
        boolean result = this.saveUsecase.apply(content);

        if (result) {
            this.editorTextAreaText.set(content);
            appendOutput(""); // TODO: Implement output
        } else {
            appendOutput(""); // TODO: Implement output
        }
    }

    public void stop() {
        boolean result = this.stopUsecase.get();

        if (result)
            appendOutput(""); // TODO: Implement output
        else
            appendOutput(""); // TODO: Implement output
    }

    public String requestOutput() {
        return this.outputUsecase.get();
    }

    public void deliverInput(String input) {
        boolean result = this.inputUsecase.apply(input);

        if (result)
            appendOutput(""); // TODO: Implement output
        else {
            this.stop();
            appendOutput(""); // TODO: Implement output
        }
    }
}
