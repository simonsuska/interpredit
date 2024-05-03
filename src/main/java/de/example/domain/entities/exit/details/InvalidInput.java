package de.example.domain.entities.exit.details;

public class InvalidInput implements Detail {
    private final String input;

    public InvalidInput(String input) {
        this.input = input;
    }

    @Override
    public String getDescription() {
        // TODO: Implement
        return null;
    }
}
