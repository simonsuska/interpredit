package de.example.domain.entities.exit.details;

public class InvalidOperand extends ProgramDetail {
    public InvalidOperand(int line, String command) {
        super(line, command);
    }

    @Override
    public String getDescription() {
        // TODO: Implement
        return null;
    }
}
