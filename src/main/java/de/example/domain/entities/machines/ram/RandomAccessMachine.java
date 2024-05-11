package de.example.domain.entities.machines.ram;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.Di;
import de.example.domain.entities.machines.Decoder;
import de.example.domain.entities.operations.Arithmetic;
import de.example.domain.entities.operations.Condition;
import de.example.domain.entities.exit.ExitStatus;
import de.example.domain.entities.Buffer;
import de.example.domain.entities.machines.Machine;

import java.util.Objects;

public class RandomAccessMachine extends Machine {
    private int pc;
    private int[] memory;

    private final Buffer<Integer> buffer;
    private final Decoder decoder;

    @Inject
    public RandomAccessMachine(
            @Named(Di.RAM_INT_BUFFER) Buffer<Integer> buffer,
            @Named(Di.RAM_DECODER) Decoder decoder) {
        this.buffer = Objects.requireNonNull(buffer);
        this.decoder = Objects.requireNonNull(decoder);
    }

    private void forward() {
        // TODO: Implement
    }

    private void branch(int address) {
        // TODO: Implement
    }

    private ExitStatus checkMemoryValidity(int address) {
        // TODO: Implement
        return null;
    }

    private ExitStatus checkProgramValidity(int address){
        // TODO: Implement
        return null;
    }

    private ExitStatus performArithmetic(int address, Arithmetic arithmetic) {
        // TODO: Implement
        return null;
    }

    private ExitStatus performBranch(int address, Condition condition) {
        // TODO: Implement
        return null;
    }

    private void reset() {
        // TODO: Implement
    }

    @Override
    public ExitStatus run(String cmd) {
        // TODO: Implement
        return null;
    }

    @Override
    public String write() {
        // TODO: Implement
        return null;
    }

    @Override
    public ExitStatus read(String input) {
        // TODO: Implement
        return null;
    }

    public ExitStatus set(int value) {
        // TODO: Implement
        return null;
    }

    public ExitStatus add(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus sub(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus mul(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus div(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus lda(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus ldk(int value) {
        // TODO: Implement
        return null;
    }

    public ExitStatus sta(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus inp(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus out(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus jmp(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus jez(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus jne(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus jlz(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus jle(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus jgz(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus jge(int address) {
        // TODO: Implement
        return null;
    }

    public ExitStatus hlt(int value) {
        // TODO: Implement
        return null;
    }
}
