module Interpredit {
    requires javafx.controls;
    requires javafx.fxml;

    opens de.example.presentation to javafx.fxml;
    exports de.example.presentation;
}