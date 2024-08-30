package de.example.presentation;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.usecases.*;
import de.example.presentation.controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Objects;

import static de.example.presentation.Interpredit.s;

/** This type is a JavaFX model. */
public class Model {

    //: SECTION: - ATTRIBUTES

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

    //: SECTION: - CONSTRUCTORS

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

    //: SECTION: - METHODS

    /** This method grants access to the content of the editor and is used by the controller to create a binding. */
    public StringProperty editorTextAreaTextProperty() {
        return editorTextAreaText;
    }

    /**
     * This method grants access to the content of the output text area and is used by the controller to create a
     * binding.
     */
    public StringProperty outputTextAreaTextProperty() {
        return outputTextAreaText;
    }

    /**
     * This method grants access to the name of the currently opened file and is used by the controller to create a
     * binding.
     */
    public StringProperty fileLabelTextProperty() {
        return fileLabelText;
    }

    /**
     * This method appends the given string to the output text area if it is not {@code null}.
     *
     * <br><br><b>Discussion</b><br>
     * It is used by the printer thread to provide output to the user during the program execution.
     *
     * @param output The string to be appended
     */
    public void appendOutput(String output) {
        if (output != null)
            outputTextAreaText.set(outputTextAreaText.get() + output + "\n");
    }

    /** This method causes the file currently open in the editor to be deleted. */
    public void deleteFile() {
        boolean result = this.deleteUsecase.get();

        if (result) {
            this.editorTextAreaText.set("");
            appendOutput(s("deleteFileSuccessMessage"));
            this.fileLabelText.set("");
        } else {
            appendOutput(s("deleteFileFailureMessage"));
        }
    }

    /**
     * This method causes the content of the file referenced by the given string to be displayed in the editor.
     *
     * @param filename The absolute path to the file
     */
    public void openFile(String filename) {
        String content = this.openUsecase.apply(filename);

        if (content != null) {
            this.editorTextAreaText.set(content);
            appendOutput(s("openFileSuccessMessage"));
            this.fileLabelText.set(filename);
        } else {
            appendOutput(s("openFileFailureMessage"));
        }
    }

    /** This method causes the file currently open in the editor to be closed, but not deleted. */
    public void closeFile() {
        boolean result = this.closeUsecase.get();

        if (result) {
            this.editorTextAreaText.set("");
            appendOutput(s("closeFileSuccessMessage"));
            this.fileLabelText.set("");
        } else {
            appendOutput(s("closeFileFailureMessage"));
        }
    }

    /**
     * This method causes the given program to be executed.
     *
     * @param program The program to be executed
     */
    public void run(String program) {
        this.runUsecase.setProgram(program);
        new Thread(Interpredit.getMessagePrinter(), "PrinterThread").start();
        new Thread(runUsecase, "RunnerThread").start();
    }

    /**
     * This method causes the content of the file currently open in the editor to be saved.
     *
     * @param content The content to be saved
     */
    public void saveFile(String content) {
        boolean result = this.saveUsecase.apply(content);

        if (result) {
            this.editorTextAreaText.set(content);
            appendOutput(s("saveFileSuccessMessage"));
        } else {
            appendOutput(s("saveFileFailureMessage"));
        }
    }

    /** This method causes the currently running program to be interrupted. */
    public void stop() {
        boolean result = this.stopUsecase.get();

        if (result)
            appendOutput(s("stopSuccessMessage"));
        else
            appendOutput(s("stopFailureMessage"));
    }

    /**
     * This method causes an output to be requested from the random access machine.
     *
     * <br><br><b>Discussion</b><br>
     * It is used by the printer thread to transmit the output of the random access machine to the user after
     * receiving the status {@code OUTPUT}.
     *
     * @return The output of the random access machine
     */
    public String requestOutput() {
        return this.outputUsecase.get();
    }

    /**
     * This method causes an input to be delivered to the random access machine.
     *
     * <br><br><b>Discussion</b><br>
     * This method is called when the user hits <i>Enter</i> after an input.
     *
     * @param input The input to be delivered to the random access machine
     */
    public void deliverInput(String input) {
        boolean result = this.inputUsecase.apply(input);

        if (result)
            appendOutput(s("deliverInputSuccessMessage", input));
        else {
            appendOutput(s("deliverInputFailureMessage", input));
        }
    }
}
