package de.example.domain.entities;

public enum ExitStatus {
    CONTINUE(""),
    INPUT("Input required. Please enter a value."),
    OUTPUT(""),
    QUIT("Program finished successfully.");

    private final String description;
    ExitStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
