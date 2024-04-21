package de.example.core.exceptions;

public class InputException extends InterpreditException {
    private final String input;

    public InputException(String input) {
        this.input = input;
    }

    @Override
    public String getDescription() {
        // TODO: Implement
        return null;
    }
}
