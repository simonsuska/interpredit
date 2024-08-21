package de.example.presentation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.example.core.di.InterpreditModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Interpredit extends Application {
    private static ResourceBundle bundle;
    private static final Injector injector = Guice.createInjector(new InterpreditModule());

    @Override
    public void start(Stage stage) throws IOException, NoSuchMethodException {
        var fxmlLoader = new FXMLLoader(Interpredit.class.getResource("interpredit.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);

        var scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Interpredit");
        stage.setScene(scene);
        stage.show();
    }

    public static String s(String key, Object... arguments) {
        String rawString = bundle.getString(key);
        return MessageFormat.format(rawString, arguments);
    }

    public static PrinterThread getPrinterThread() {
        return injector.getInstance(PrinterThread.class);
    }

    public static void main(String[] args) {
        Locale locale = Locale.getDefault();
        bundle = ResourceBundle.getBundle("strings", locale);

        System.out.print("\n");
        System.out.println("+-+ +-+  +-+ +-----+ +-----+ +-----   +-----  +-----   +-----+ +----   +-+ +-----+");
        System.out.println("| | |  \\ | | +-+ +-+ | +---+ |     \\  |     \\ |     \\  | +---+ |    \\  | | +-+ +-+");
        System.out.println("| | |   \\| |   | |   | +--   |     /  |     / |     /  | +--   | |\\  | | |   | |     ");
        System.out.println("| | | |\\   |   | |   | +--   | |\\ \\   | +---  | |\\ \\   | +--   | |/  | | |   | |");
        System.out.println("| | | | \\  |   | |   | +---+ | | \\ \\  | |     | | \\ \\  | +---+ |    /  | |   | |");
        System.out.println("+-+ +-+  +-+   +-+   +-----+ +-+  +-+ +-+     +-+  +-+ +-----+ +----   +-+   +-+");
        System.out.print("\n");

        launch();
    }
}
