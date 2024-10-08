package de.example.presentation.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.FontChecker;
import de.example.core.di.Di;
import de.example.presentation.Model;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static de.example.core.FontChecker.COURIER_NEW_FONT_NAME;
import static de.example.core.FontChecker.SF_MONO_FONT_NAME;
import static de.example.presentation.Interpredit.s;

/** This type is a JavaFX controller. */
public class Controller {

    //: SECTION: - ATTRIBUTES

    private static final Font SF_MONO = Font.font(SF_MONO_FONT_NAME, FontWeight.NORMAL, FontPosture.REGULAR, 12);
    private static final Font COURIER_NEW = Font.font(COURIER_NEW_FONT_NAME, FontWeight.BOLD, FontPosture.REGULAR, 13);

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

    /** This attribute is used to check whether a given font is available on the system. */
    @Inject @Named(Di.FONT_CHECKER)
    private FontChecker fontChecker;

    /** This attribute is used to inform the finisher thread that the program has ended. */
    @Inject @Named(Di.QUIT_CYCLIC_BARRIER)
    private CyclicBarrier stopSignal;

    @Inject @Named(Di.MODEL)
    private Model model;

    //: SECTION: - METHODS

    /** This method initializes the user interface. */
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

        if (fontChecker.isFontAvailable(SF_MONO_FONT_NAME)) {
            this.editorTextArea.setFont(SF_MONO);
            this.inputTextField.setFont(SF_MONO);
            this.outputTextArea.setFont(SF_MONO);
        } else if (fontChecker.isFontAvailable(COURIER_NEW_FONT_NAME)) {
            this.editorTextArea.setFont(COURIER_NEW);
            this.inputTextField.setFont(COURIER_NEW);
            this.outputTextArea.setFont(COURIER_NEW);
        }
    }

    /** This method is called when the user clicks on the {@code closeAppMenuItem}. */
    @FXML private void close() {
        Platform.exit();
    }

    /** This method is called when the user clicks on the {@code deleteFileMenuItem}. */
    @FXML private void deleteFile() {
        this.model.deleteFile();
    }

    /** This method is called when the user clicks on the {@code openFileMenuItem}. */
    @FXML private void openFile() {
        File file = new FileChooser().showOpenDialog(this.editorTextArea.getScene().getWindow());

        if (file != null) {
            this.outputTextArea.setText("");
            this.model.openFile(file.getAbsolutePath());
        }
    }

    /** This method is called when the user clicks on the {@code closeFileMenuItem}. */
    @FXML private void closeFile() {
        this.model.closeFile();
    }

    /** This method is called when the user clicks on the {@code runFileMenuItem}. */
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

    /** This method is called when the user clicks on the {@code saveFileMenuItem}. */
    @FXML private void saveFile() {
        String content = this.editorTextArea.getText();
        this.model.saveFile(content);
    }

    /** This method is called when the user clicks on the {@code stopMenuItem}. */
    @FXML private void stop() {
        this.model.stop();
    }
}
