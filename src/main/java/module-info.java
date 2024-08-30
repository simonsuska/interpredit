open module Interpredit {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.vavr;
    requires com.google.guice;
    requires jdk.jfr;

    exports de.example.core;
    exports de.example.presentation;
    exports de.example.presentation.controller;
    exports de.example.domain.entities;
    exports de.example.domain.entities.machines;
    exports de.example.domain.repository;
    exports de.example.domain.usecases;
}
