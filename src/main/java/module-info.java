open module Interpredit {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.vavr;
    requires com.google.guice;

    //opens de.example.presentation to javafx.fxml;
    exports de.example.presentation;
    exports de.example.domain.usecases;
    exports de.example.domain.entities.machines;
    exports de.example.domain.entities.exit.builder;
    exports de.example.domain.entities.exit.details;
    exports de.example.domain.entities.exit.status;
    //opens de.example.presentation.controller to javafx.fxml;
}