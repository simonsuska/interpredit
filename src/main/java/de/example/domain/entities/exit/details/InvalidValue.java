package de.example.domain.entities.exit.details;

public class InvalidValue extends ProgramDetail {
    public InvalidValue(int line, String command) {
        super(line, command);
    }

    @Override
    public String getDescription() {
        // TODO: Implement
        return null;
    }
}
