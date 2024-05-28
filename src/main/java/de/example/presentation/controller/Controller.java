package de.example.presentation.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.Di;
import de.example.presentation.Model;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
        // TODO: Implement
        Binding<String> outputTextAreaBinding = Bindings.createStringBinding(
                () -> this.outputTextArea.getText() + this.model.outputTextAreaTextProperty().get(),
                this.model.outputTextAreaTextProperty()
        );
        this.outputTextArea.textProperty().bind(outputTextAreaBinding);
        this.fileLabel.textProperty().bind(this.model.openFilenameProperty());
    }

    @FXML private void close() {
        // TODO: Implement
    }

    @FXML private void delete() {
        // TODO: Implement
    }

    @FXML private void openFile() {
        // TODO: Implement
    }

    @FXML private void run() {
        // TODO: Implement
    }

    @FXML private void save() {
        // TODO: Implement
    }

    @FXML private void stop() {
        // TODO: Implement
    }
}
