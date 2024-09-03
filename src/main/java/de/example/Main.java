package de.example;

import de.example.presentation.Interpredit;

/**
 * This type is used as a launcher class to trick the JavaFX runtime into allowing itself to run from the classpath
 * instead of the module path. This makes it possible to use the maven shade plugin.
 */
public class Main {
    public static void main(String[] args) {
        Interpredit.main(args);
    }
}
