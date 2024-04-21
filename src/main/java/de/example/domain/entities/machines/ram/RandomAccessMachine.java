package de.example.domain.entities.machines.ram;

import de.example.core.exceptions.InputException;
import de.example.core.exceptions.InterpreditException;
import de.example.core.exceptions.commandExecutionExceptions.InvalidAddressException;
import de.example.core.exceptions.commandExecutionExceptions.InvalidValueException;
import de.example.domain.entities.Arithmetic;
import de.example.domain.entities.Condition;
import de.example.domain.entities.ExitStatus;
import de.example.domain.entities.buffers.IntBuffer;
import de.example.domain.entities.machines.Machine;

public class RandomAccessMachine extends Machine {
    private int pc;
    private IntBuffer buffer;
    private int[] memory;

    private RandomAccessMachineCommand[] program;
    private RandomAccessMachineDecoder decoder;

    private void forward() {
        // TODO: Implement
    }

    private void branch(int address) {
        // TODO: Implement
    }

    private void checkMemoryValidity(int address) throws InvalidAddressException {
        // TODO: Implement
    }

    private void checkProgramValidity(int address) throws InvalidAddressException {
        // TODO: Implement
    }

    private ExitStatus performArithmetic(int address, Arithmetic arithmetic) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    private ExitStatus performBranch(int address, Condition condition) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    private void reset() {
        // TODO: Implement
    }

    @Override
    public ExitStatus run(String program) throws InterpreditException {
        // TODO: Implement
        return null;
    }

    public ExitStatus resume() throws InterpreditException {
        // TODO: Implement
        return null;
    }

    @Override
    public String write() {
        // TODO: Implement
        return null;
    }

    @Override
    public void read(String input) throws InputException {
        // TODO: Implement
    }

    public ExitStatus set(int value) throws InvalidValueException {
        // TODO: Implement
        return null;
    }

    public ExitStatus add(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus sub(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus mul(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus div(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus lda(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus ldk(int value) {
        // TODO: Implement
        return null;
    }

    public ExitStatus sta(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus inp(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus out(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus jmp(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus jez(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus jne(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus jlz(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus jle(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus jgz(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus jge(int address) throws InvalidAddressException {
        // TODO: Implement
        return null;
    }

    public ExitStatus hlt(int value) {
        // TODO: Implement
        return null;
    }
}
