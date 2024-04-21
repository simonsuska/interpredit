package de.example.presentation;

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

    private Model model;
    private Thread executionThread;

    @FXML private void initialize() {
        // TODO: Implement
    }

    @FXML private void close() {
        // TODO: Implement
    }

    @FXML private void openFile() {
        // TODO: Implement
    }

    @FXML private void save() {
        // TODO: Implement
    }

    @FXML private void delete() {
        // TODO: Implement
    }

    @FXML private void run() {
        // TODO: Implement
    }

    @FXML private void stop() {
        // TODO: Implement
    }
}
