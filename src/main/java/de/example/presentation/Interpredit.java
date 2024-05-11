package de.example.presentation;

import com.google.inject.Guice;
import de.example.core.InterpreditModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Interpredit extends Application {
    @Override
    public void start(Stage stage) throws IOException, NoSuchMethodException {
        var injector = Guice.createInjector(new InterpreditModule());
        var fxmlLoader = new FXMLLoader(Interpredit.class.getResource("interpredit.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);

        var scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Interpredit");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
