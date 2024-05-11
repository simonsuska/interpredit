open module Interpredit {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.vavr;
    requires com.google.guice;

    //opens de.example.presentation to javafx.fxml;
    exports de.example.presentation;
    exports de.example.presentation.controller;
    //opens de.example.presentation.controller to javafx.fxml;
}