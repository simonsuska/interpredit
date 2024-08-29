package de.example.presentation.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.presentation.Model;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static de.example.presentation.Interpredit.s;

/** This type is a JavaFX controller. */
public class Controller {

    //: SECTION: - ATTRIBUTES

    @FXML private Menu interpreditMenu;
    @FXML private Menu fileMenu;

    @FXML private MenuItem closeAppMenuItem;

    @FXML private MenuItem openFileMenuItem;
    @FXML private MenuItem closeFileMenuItem;
    @FXML private MenuItem saveFileMenuItem;
    @FXML private MenuItem deleteFileMenuItem;
    @FXML private MenuItem runFileMenuItem;
    @FXML private MenuItem stopMenuItem;

    @FXML private Label fileLabel;

    @FXML private TextField inputTextField;
    @FXML private TextArea editorTextArea;
    @FXML private TextArea outputTextArea;

    /** This attribute is used to inform the {@code FinisherThread} that the program has ended. */
    @Inject @Named(Di.QUIT_CYCLIC_BARRIER)
    private CyclicBarrier stopSignal;

    @Inject @Named(Di.MODEL)
    private Model model;

    //: SECTION: - METHODS

    @FXML private void initialize() {
        this.editorTextArea.textProperty().bindBidirectional(this.model.editorTextAreaTextProperty());
        this.outputTextArea.textProperty().bindBidirectional(this.model.outputTextAreaTextProperty());
        this.fileLabel.textProperty().bind(this.model.fileLabelTextProperty());

        this.inputTextField.setOnKeyPressed(
                (keyEvent -> {
                    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                        this.model.deliverInput(this.inputTextField.getText());
                        this.fileLabel.requestFocus();
                        this.inputTextField.setText("");
                    }
                })
        );

        this.interpreditMenu.setText(s("interpreditMenuText"));
        this.fileMenu.setText(s("fileMenuText"));

        this.closeAppMenuItem.setText(s("closeMenuItemText"));

        this.openFileMenuItem.setText(s("openFileMenuItemText"));
        this.closeFileMenuItem.setText(s("closeFileMenuItemText"));
        this.saveFileMenuItem.setText(s("saveFileMenuItemText"));
        this.deleteFileMenuItem.setText(s("deleteFileMenuItemText"));
        this.runFileMenuItem.setText(s("runFileMenuItemText"));
        this.stopMenuItem.setText(s("stopMenuItemText"));
    }

    /** This method is called when the user clicks on the {@code closeAppMenuItem} */
    @FXML private void close() {
        Platform.exit();
    }

    /** This method is called when the user clicks on the {@code deleteFileMenuItem} */
    @FXML private void deleteFile() {
        this.model.deleteFile();
    }

    /** This method is called when the user clicks on the {@code openFileMenuItem} */
    @FXML private void openFile() {
        File file = new FileChooser().showOpenDialog(this.editorTextArea.getScene().getWindow());

        if (file != null) {
            this.outputTextArea.setText("");
            this.model.openFile(file.getAbsolutePath());
        }
    }

    /** This method is called when the user clicks on the {@code closeFileMenuItem} */
    @FXML private void closeFile() {
        this.model.closeFile();
    }

    /**
     * This method is called when the user clicks on the {@code runFileMenuItem}
     *
     * <br><br><b>Discussion</b><br>
     * During the execution of the program, there are three further threads
     * in addition to the main thread.
     * <ol>
     *     <li>
     *         {@code RunnerThread} (aka. {@code RunUsecase})
     *         <br>
     *         This thread is started by the model and executes the program line by line.
     *     </li>
     *     <li>
     *         {@code PrinterThread}
     *         <br>
     *         This thread is also started by the model, directly before the {@code RunnerThread},
     *         and provides output to the user during the program execution. It receives the
     *         current status of the {@code RunnerThread} via an exchanger object, whereupon it
     *         triggers the output of a corresponding message in the main thread.
     *     </li>
     *     <li>
     *         {@code FinisherThread}
     *         <br>
     *         This thread is started by the controller and waits for the program to finish. It is
     *         informed of the end by the {@code RunnerThread} via a cyclic barrier, whereupon it
     *         enables the {@code runFileMenuItem} and disables the {@code stopMenuItem}.
     *     </li>
     * </ol>
     *
     * After the {@code FinisherThread} has been notified via the cyclic barrier, it executes its
     * commands and is thus terminated. The {@code RunnerThread} and the {@code PrinterThread} leave
     * their loop as soon as a status indicates an error or the end of the program. As the
     * {@code RunnerThread} transmits every status that is not 'OK' to the {@code PrinterThread},
     * these two threads are also terminated at the end of a program, leaving only the main thread
     * after the program execution.
     * @see Model#run(String)
     * @see de.example.presentation.PrinterThread
     * @see de.example.domain.usecases.RunUsecase
     */
    @FXML private void runFile() {
        String program = this.editorTextArea.getText();
        if (program.isBlank()) {
            this.model.appendOutput(s("runFileFailureMessage"));
            return;
        }

        this.outputTextArea.setText("");
        this.runFileMenuItem.setDisable(true);
        this.stopMenuItem.setDisable(false);

        this.model.run(program);
        new Thread(() -> {
            try {
                stopSignal.await();
                Platform.runLater(() -> runFileMenuItem.setDisable(false));
                Platform.runLater(() -> stopMenuItem.setDisable(true));
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }, "FinisherThread").start();
    }

    /** This method is called when the user clicks on the {@code saveFileMenuItem} */
    @FXML private void saveFile() {
        String content = this.editorTextArea.getText();
        this.model.saveFile(content);
    }

    /** This method is called when the user clicks on the {@code stopMenuItem} */
    @FXML private void stop() {
        this.model.stop();
    }
}
