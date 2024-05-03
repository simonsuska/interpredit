package de.example.domain.entities.exit.details;

public abstract class ProgramDetail implements Detail {
    private final int line;
    private final String command;

    public ProgramDetail(int line, String command) {
        this.line = line;
        this.command = command;
    }

    public int getLine() {
        return this.line;
    }

    public String getCommand() {
        return this.command;
    }
}
