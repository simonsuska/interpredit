package de.example.presentation.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.presentation.Model;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {
    @FXML private MenuItem runFileMenuItem;
    @FXML private MenuItem stopExecutionMenuItem;

    @FXML private Label fileLabel;

    @FXML private TextField inputTextField;
    @FXML private TextArea editorTextArea;
    @FXML private TextArea outputTextArea;

    @Inject @Named(Di.MODEL)
    private Model model;

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
    }

    @FXML private void close() {
        Platform.exit();
    }

    @FXML private void deleteFile() {
        this.model.deleteFile();
    }

    @FXML private void openFile() {
        File file = new FileChooser().showOpenDialog(this.editorTextArea.getScene().getWindow());

        if (file != null) {
            this.model.openFile(file.getAbsolutePath());
        }
    }

    @FXML private void closeFile() {
        this.model.closeFile();
    }

    @FXML private void run() {
        this.outputTextArea.setText("");
        this.runFileMenuItem.setDisable(true);
        this.stopExecutionMenuItem.setDisable(false);

        String program = this.editorTextArea.getText();
        this.model.run(program);

        // TODO: Put in separate thread
        this.runFileMenuItem.setDisable(false);
        this.stopExecutionMenuItem.setDisable(true);
    }

    @FXML private void saveFile() {
        String content = this.editorTextArea.getText();
        this.model.saveFile(content);
    }

    @FXML private void stop() {
        this.model.stop();
        this.runFileMenuItem.setDisable(false);
        this.stopExecutionMenuItem.setDisable(true);
    }
}
