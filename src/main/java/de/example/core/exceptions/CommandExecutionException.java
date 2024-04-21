package de.example.core.exceptions;

public abstract class CommandExecutionException extends InterpreditException {
    private String command;
    private int pc;

    public String getCommand() {
        return this.command;
    }

    public int getPc() {
        return this.pc;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }
}
