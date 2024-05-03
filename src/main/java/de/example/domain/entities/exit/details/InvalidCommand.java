package de.example.domain.entities.exit.details;

public class InvalidCommand extends ProgramDetail {
    public InvalidCommand(int line, String command) {
        super(line, command);
    }

    @Override
    public String getDescription() {
        // TODO: Implement
        return null;
    }
}
