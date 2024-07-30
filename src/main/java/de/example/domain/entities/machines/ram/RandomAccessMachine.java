package de.example.domain.entities.machines.ram;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.entities.Status;
import de.example.domain.entities.machines.Decoder;
import de.example.domain.entities.operations.Arithmetic;
import de.example.domain.entities.operations.Condition;
import de.example.domain.entities.Buffer;
import de.example.domain.entities.machines.Machine;

import java.util.Objects;

public class RandomAccessMachine extends Machine {
    private int pc;
    private int[] memory;

    private final Buffer<Integer> buffer;
    private final Decoder decoder;

    private boolean notifiedAboutInput = false;

    @Inject
    public RandomAccessMachine(
            @Named(Di.RAM_INT_BUFFER) Buffer<Integer> buffer,
            @Named(Di.RAM_DECODER) Decoder decoder) {
        this.buffer = Objects.requireNonNull(buffer);
        this.decoder = Objects.requireNonNull(decoder);
    }

    public RandomAccessMachine(int[] memory, Buffer<Integer> buffer, Decoder decoder) {
        this.memory = memory;
        this.buffer = buffer;
        this.decoder = decoder;
    }

    private void forward() {
        this.pc++;
    }

    private void branch(int address) {
        this.pc = address - 1;
    }

    private boolean isAddressWithinBounds(int address) {
        return address > 0 && address < this.memory.length;
    }

    private Status performArithmetic(int address, Arithmetic arithmetic) {
        if (this.isAddressWithinBounds(address)) {
            switch (arithmetic) {
                case ADD -> this.memory[0] += this.memory[address];
                case SUB -> this.memory[0] -= this.memory[address];
                case MUL -> this.memory[0] *= this.memory[address];
                case DIV -> {
                    if (this.memory[address] != 0) this.memory[0] /= this.memory[address];
                    else return Status.DIVISION_BY_ZERO_ERROR;
                }
            }

            this.forward();
            return Status.OK;
        }

        return Status.MEMORY_ADDRESS_ERROR;
    }

    private Status performBranch(int address, Condition condition) {
        switch (condition) {
            case NN -> this.branch(address);
            case EZ -> { if (this.memory[0] == 0) { this.branch(address); } else { this.forward(); } }
            case NE -> { if (this.memory[0] != 0) { this.branch(address); } else { this.forward(); } }
            case LZ -> { if (this.memory[0] < 0) { this.branch(address); } else { this.forward(); } }
            case LE -> { if (this.memory[0] <= 0) { this.branch(address); } else { this.forward(); } }
            case GZ -> { if (this.memory[0] > 0) { this.branch(address); } else { this.forward(); } }
            case GE -> { if (this.memory[0] >= 0) { this.branch(address); } else { this.forward(); } }
        }

        return Status.OK;
    }

    @Override
    public int getPc() {
        return this.pc;
    }

    @Override
    public Status run(String cmd) {
        RandomAccessMachineCommand command = (RandomAccessMachineCommand) this.decoder.decode(cmd);
        return command == null ? Status.DECODE_ERROR : command.execute(this);
    }

    @Override
    public void reset() {
        super.reset();

        this.pc = 0;
        this.memory = null;
        this.buffer.write(null);
    }

    @Override
    public String requestOutput() {
        // Called only from PrinterThread
        Integer value = 0;

        try {
            value = this.buffer.read();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return String.valueOf(value);
    }

    @Override
    public boolean deliverInput(String input) {
        // Called only from Main Thread
        try {
            int inputValue = Integer.parseInt(input == null
                    ? input
                    : input.trim());
            this.buffer.write(inputValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Status set(int value) {
        if (value < 0)
            return Status.SET_ERROR;

        this.memory = new int[value+1];
        this.forward();
        return Status.OK;
    }

    public Status add(int address) {
        return this.performArithmetic(address, Arithmetic.ADD);
    }

    public Status sub(int address) {
        return this.performArithmetic(address, Arithmetic.SUB);
    }

    public Status mul(int address) {
        return this.performArithmetic(address, Arithmetic.MUL);
    }

    public Status div(int address) {
        return this.performArithmetic(address, Arithmetic.DIV);
    }

    public Status lda(int address) {
        if (this.isAddressWithinBounds(address)) {
            this.memory[0] = this.memory[address];
            this.forward();
            return Status.OK;
        }

        return Status.MEMORY_ADDRESS_ERROR;
    }

    public Status ldk(int value) {
        this.memory[0] = value;
        this.forward();
        return Status.OK;
    }

    public Status sta(int address) {
        if (this.isAddressWithinBounds(address)) {
            this.memory[address] = this.memory[0];
            this.forward();
            return Status.OK;
        }

        return Status.MEMORY_ADDRESS_ERROR;
    }

    public Status inp(int address) {
        // Called only from RunnerThread
        if (this.isAddressWithinBounds(address)) {
            if (buffer.isEmpty()) {
                if (!notifiedAboutInput) {
                    notifiedAboutInput = true;
                    return Status.INPUT;
                }
                notifiedAboutInput = false;

                try {
                    System.out.println("Runner Thread waiting on read");
                    this.memory[address] = this.buffer.read();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.forward();
            return Status.OK;
        }

        return Status.MEMORY_ADDRESS_ERROR;
    }

    public Status out(int address) {
        // Called only from RunnerThread
        if (this.isAddressWithinBounds(address)) {
            this.buffer.write(this.memory[address]);
            this.forward();
            return Status.OUTPUT;
        }

        return Status.MEMORY_ADDRESS_ERROR;
    }

    public Status jmp(int address) {
        return this.performBranch(address, Condition.NN);
    }

    public Status jez(int address) {
        return this.performBranch(address, Condition.EZ);
    }

    public Status jne(int address) {
        return this.performBranch(address, Condition.NE);
    }

    public Status jlz(int address) {
        return this.performBranch(address, Condition.LZ);
    }

    public Status jle(int address) {
        return this.performBranch(address, Condition.LE);
    }

    public Status jgz(int address) {
        return this.performBranch(address, Condition.GZ);
    }

    public Status jge(int address) {
        return this.performBranch(address, Condition.GE);
    }

    public Status hlt(int value) {
        return Status.FINISH;
    }
}
