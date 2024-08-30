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

    //: SECTION: - ATTRIBUTES

    private static ResourceBundle bundle;
    private static final Injector injector = Guice.createInjector(new InterpreditModule());

    //: SECTION: - METHODS

    /**
     * This method grants access to the messages of the resource bundle of the current locale.
     *
     * <br><br><b>Discussion</b><br>
     * The basic idea behind this method is to make the access to the resource bundle as simple and unobtrusive as
     * possible.
     *
     * @param key The key of the message in the resource bundle
     * @param arguments Arguments to fill placeholders in the message with dynamic values
     * @return The formatted message of the given key together with the passed arguments
     */
    public static String s(String key, Object... arguments) {
        String rawString = bundle.getString(key);
        return MessageFormat.format(rawString, arguments);
    }

    /**
     * This method returns the registered message printer object.
     *
     * <br><br><b>Discussion</b><br>
     * It is used by the model to start the printer thread in which the message printer is executed.
     *
     * @return The registered message printer object
     */
    public static MessagePrinter getMessagePrinter() {
        return injector.getInstance(MessagePrinter.class);
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

    @Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(Interpredit.class.getResource("interpredit.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);

        var scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Interpredit");
        stage.setScene(scene);
        stage.show();
    }
}
