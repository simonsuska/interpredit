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

/** This type represents a random access machine. */
public class RandomAccessMachine extends Machine {

    //: SECTION: - ATTRIBUTES

    /**
     * This attribute contains the current program counter.
     *
     * <br><br><b>Discussion</b><br>
     * The {@code RunnerThread} (aka {@code RunUsecase}) uses this one
     * to access the proper line of code.
     * <br><br>
     * The program counter is in the value range from 0 to the number of
     * lines of code in the editor minus 1. This is because it simplifies
     * the access to the list containing the coded commands. However,
     * the user uses a value range from 1 to the number of lines of code in
     * the editor, as this is more intuitive. For this reason, the program
     * counter is set to the address minus 1 for all commands in which the
     * user uses a program address.
     */
    private int pc;

    /**
     * This attribute represents the internal memory of the random access machine including
     * the accumulator, which is located at index 0.
     */
    private int[] memory;

    /**
     * This attribute is used to receive an input from or
     * provide an output to the user.
     */
    private final Buffer<String> buffer;
    private final Decoder decoder;

    /**
     * This attribute is used to check whether the user has already been
     * informed to enter a value before the program waits for it.
     */
    private boolean notifiedAboutInput = false;

    //: SECTION: - CONSTRUCTORS

    @Inject
    public RandomAccessMachine(
            @Named(Di.RAM_STRING_BUFFER) Buffer<String> buffer,
            @Named(Di.RAM_DECODER) Decoder decoder) {
        this.buffer = Objects.requireNonNull(buffer);
        this.decoder = Objects.requireNonNull(decoder);
    }

    public RandomAccessMachine(int[] memory, Buffer<String> buffer, Decoder decoder) {
        this.memory = memory;
        this.buffer = buffer;
        this.decoder = decoder;
    }

    //: SECTION: - METHODS

    /**
     * This method interrupts the machine and closes the buffer.
     *
     * <br><br><b>Discussion</b><br>
     * In this regard, closing the buffer is crucial to wake up the {@code RunnerThread}
     * (aka {@code RunUsecase}) from the blocking state.
     */
    @Override
    public synchronized void interrupt() {
        super.interrupt();
        this.buffer.close();
    }

    /** This method increments the program counter. */
    private void forward() {
        this.pc++;
    }

    /**
     * This method sets the program counter to the given program address.
     *
     * <br><br><b>Discussion</b><br>
     * Since the given program address is user-generated, it is decremented before
     * storing it in the program counter.
     * @param address The program address to be set
     * @see RandomAccessMachine#pc
     */
    private void branch(int address) {
        this.pc = address - 1;
    }

    /**
     * This method checks if the given memory address is within the memory accessible to the user,
     * which is in the interval between 1 and the set value, including the limits.
     * @param address The memory address to be checked
     * @return {@code true} if the memory address is within the memory accessible to the user,
     *         otherwise {@code false}
     */
    private boolean isMemoryAddressWithinBounds(int address) {
        return address > 0 && address < this.memory.length;
    }

    /**
     * This method causes the given arithmetic operation to be executed.
     *
     * <br><br><b>Discussion</b><br>
     * More concrete, the value behind the given memory address will be offset against the value inside the accumulator.
     * The result is stored in the accumulator, which means that the previous value is overwritten.
     * @param address The memory address containing the value which is offset against the
     *                value inside the accumulator
     * @param arithmetic The arithmetic operation to be executed
     * @return The status after executing the operation
     */
    private Status performArithmetic(int address, Arithmetic arithmetic) {
        if (this.isMemoryAddressWithinBounds(address)) {
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

    /**
     * This method causes the given jump to be executed.
     *
     * <br><br><b>Discussion</b><br>
     * More concrete, the program counter will be set to the given program address
     * minus 1 if the condition is met. Otherwise, it will be incremented.
     * @param address The line of code to be jumped to, which is equal to the
     *                program address minus 1
     * @param condition The condition to be met
     * @return Always {@code Status.OK}
     */
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

    /**
     * This method returns the current program counter in the value range
     * from 0 to the number of lines of code in the editor minus 1.
     *
     * <br><br><b>Discussion</b><br>
     * In the context of Interpredit, it is used by the {@code RunnerThread} (aka. {@code RunUsecase})
     * to access the proper line of code while the program is executed.
     * @return The current program counter
     */
    @Override
    public int getPc() {
        return this.pc;
    }

    /**
     * This method decodes the given command and subsequently executes it on this machine.
     * @param cmd The encoded command to be executed
     * @return The status after executing the command
     */
    @Override
    public Status run(String cmd) {
        RandomAccessMachineCommand command = (RandomAccessMachineCommand) this.decoder.decode(cmd);
        return command == null ? Status.DECODE_ERROR : command.execute(this);
    }

    /**
     * This method resets the machine, which includes the program counter,
     * the memory and the buffer.
     */
    @Override
    public void reset() {
        super.reset();

        this.pc = 0;
        this.memory = null;
        this.buffer.reset();
    }

    /**
     * This method requests an output from the machine by reading the buffer.
     *
     * <br><br><b>Discussion</b><br>
     * Note that this method is blocking, which means it blocks the corresponding
     * thread as long as the buffer is empty. It only continues when the buffer has
     * either been filled or closed.
     * @return The requested output or {@code null}, if the buffer was closed.
     */
    @Override
    public String requestOutput() {
        // Called only from PrinterThread
        String value = "";

        try {
            value = this.buffer.read();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return value;
    }

    /**
     * This method deliver the given string as an input to the machine.
     *
     * <br><br><b>Discussion</b><br>
     * This method is called when the user hits 'Enter' after an input.
     * @param input The input to be delivered
     * @return {@code true} if the input is not {@code null},
     *         otherwise {@code false}
     */
    @Override
    public boolean deliverInput(String input) {
        // Called only from Main Thread
        if (input != null) {
            this.buffer.write(input);
            return true;
        }

        // If the input is `null`, close the buffer to notify
        // the `RunnerThread` to prevent is from being stuck.
        this.buffer.close();
        return false;
    }

    /**
     * This method initializes the memory of the machine with the
     * given number of fields and increments the program counter.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code SET}.
     * It is necessary at the beginning of every program.
     * @param value The number of fields of the memory accessible to the user
     * @return {@code Status.OK} if the memory was successfully initialized,
     *         otherwise {@code Status.SET_ERROR}
     */
    public Status set(int value) {
        if (value < 0 || value > 100)
            return Status.SET_ERROR;

        this.memory = new int[value+1];
        this.forward();
        return Status.OK;
    }

    /**
     * This method only increments the program counter.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that is not accessible to the user. It is called
     * implicitly when the user enters a line that does not contain any text. The
     * command causes the line to be skipped.
     * @param value Unused, only for conformity purposes
     * @return Always {@code Status.OK}
     */
    public Status hop(int value) {
        this.forward();
        return Status.OK;
    }

    /**
     * This method executes an addition.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code ADD}.
     * The value in the given memory address will be added to the value inside the accumulator.
     * The result is stored in the accumulator, which means that the previous value is overwritten.
     * @param address The memory address containing the value which is added
     *                to the value inside the accumulator
     * @return {@code Status.OK} if the memory address is within the accessible memory,
     *         otherwise {@code Status.MEMORY_ADDRESS_ERROR}
     */
    public Status add(int address) {
        return this.performArithmetic(address, Arithmetic.ADD);
    }

    /**
     * This method executes a subtraction.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code SUB}.
     * The value in the given memory address will be subtracted from the value inside the accumulator.
     * The result is stored in the accumulator, which means that the previous value is overwritten.
     * @param address The memory address containing the value which is subtracted
     *                from the value inside the accumulator
     * @return {@code Status.OK} if the memory address is within the accessible memory,
     *         otherwise {@code Status.MEMORY_ADDRESS_ERROR}
     */
    public Status sub(int address) {
        return this.performArithmetic(address, Arithmetic.SUB);
    }

    /**
     * This method executes a multiplication.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code MUL}.
     * The value in the given memory address will be multiplied to the value inside the accumulator.
     * The result is stored in the accumulator, which means that the previous value is overwritten.
     * @param address The memory address containing the value which is multiplied
     *                to the value inside the accumulator
     * @return {@code Status.OK} if the memory address is within the accessible memory,
     *         otherwise {@code Status.MEMORY_ADDRESS_ERROR}
     */
    public Status mul(int address) {
        return this.performArithmetic(address, Arithmetic.MUL);
    }

    /**
     * This method executes a division.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code DIV}.
     * The value in the given memory address will be divided from the value inside the accumulator.
     * The result is stored in the accumulator, which means that the previous value is overwritten.
     * @param address The memory address containing the value which is divided
     *                from the value inside the accumulator
     * @return {@code Status.OK} if the memory address is within the accessible memory and
     *         contains a value that is not 0, otherwise {@code Status.MEMORY_ADDRESS_ERROR}
     *         or {@code Status.DIVISION_BY_ZERO_ERROR}
     */
    public Status div(int address) {
        return this.performArithmetic(address, Arithmetic.DIV);
    }

    /**
     * This method loads the value stored in the given memory address into the accumulator.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code LDA}.
     * The value in the given memory address will be loaded into the accumulator,
     *  which means that the previous value inside the accumulator is overwritten.
     * @param address The memory address containing the value which is loaded
     *                into the accumulator
     * @return {@code Status.OK} if the memory address is within the accessible memory,
     *         otherwise {@code Status.MEMORY_ADDRESS_ERROR}
     */
    public Status lda(int address) {
        if (this.isMemoryAddressWithinBounds(address)) {
            this.memory[0] = this.memory[address];
            this.forward();
            return Status.OK;
        }

        return Status.MEMORY_ADDRESS_ERROR;
    }

    /**
     * This method loads the given value into the accumulator.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code LDK}.
     * The value will be loaded into the accumulator, which means
     * that the previous value inside the accumulator is overwritten.
     * @param value The value which is loaded into the accumulator
     * @return Always {@code Status.OK}
     */
    public Status ldk(int value) {
        this.memory[0] = value;
        this.forward();
        return Status.OK;
    }

    /**
     * This method stores the value inside the accumulator in the given memory address.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code STA}.
     * The value inside the accumulator is stored in the given memory address,
     * which means that the previous value in the given memory address is overwritten.
     * @param address The memory address the value inside the accumulator is stored in
     * @return {@code Status.OK} if the memory address is within the accessible memory,
     *         otherwise {@code Status.MEMORY_ADDRESS_ERROR}
     */
    public Status sta(int address) {
        if (this.isMemoryAddressWithinBounds(address)) {
            this.memory[address] = this.memory[0];
            this.forward();
            return Status.OK;
        }

        return Status.MEMORY_ADDRESS_ERROR;
    }

    /**
     * This method causes the machine to request an input from the
     * user and subsequently waits, until it is delivered.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code INP}.
     * Note that this method is blocking, which means it blocks the
     * corresponding thread as long as the buffer is empty. It only
     * continues when the buffer has either been filled or closed.
     * Furthermore, this method is invoked twice if the buffer is empty. In the first
     * invocation, {@code Status.INPUT} is returned to inform the user that he has to
     * enter a value. The second invocation then waits blocking for a specific input.
     * @param address The address in which the user input is written to
     * @return
     * <ul>
     *  <li>
     *      {@code Status.MEMORY_ADDRESS_ERROR} if the given memory address
     *      is not within the accessible memory.
     *  </li>
     *  <li>
     *      {@code Status.INPUT_ERROR} if the delivered user input is invalid,
     *      which means it is not an integer.
     *  </li>
     *  <li>
     *      {@code Status.INPUT} if the buffer is empty and the user has not
     *      yet been notified to enter an input.
     *  </li>
     *  <li>
     *      {@code Status.OK} if the buffer contains a value which is an integer
     *  </li>
     * </ul>
     */
    public Status inp(int address) {
        // Called only from RunnerThread
        if (this.isMemoryAddressWithinBounds(address)) {
            if (buffer.isEmpty()) {
                if (!notifiedAboutInput) {
                    notifiedAboutInput = true;
                    return Status.INPUT;
                }
                notifiedAboutInput = false;

                try {
                    String input = this.buffer.read();

                    if (input == null)
                        return Status.INPUT_ERROR;

                    int intInput = Integer.parseInt(input.trim());
                    this.memory[address] = intInput;
                } catch (InterruptedException | NumberFormatException e) {
                    return Status.INPUT_ERROR;
                }
            }

            this.forward();
            return Status.OK;
        }

        return Status.MEMORY_ADDRESS_ERROR;
    }

    /**
     * This method informs the {@code RunnerThread} (aka. {@code RunUsecase})
     * that an output is to be made.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code OUT}.
     * @param address The memory address containing the value to be output
     * @return {@code Status.OK} if the memory address is within the accessible memory,
     *         otherwise {@code Status.MEMORY_ADDRESS_ERROR}
     */
    public Status out(int address) {
        // Called only from RunnerThread
        if (this.isMemoryAddressWithinBounds(address)) {
            this.buffer.write(String.valueOf(this.memory[address]));
            this.forward();
            return Status.OUTPUT;
        }

        return Status.MEMORY_ADDRESS_ERROR;
    }

    /**
     * This method sets the program counter to the given program address minus 1.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code JMP}.
     * Note that the program is trapped in an infinite loop if you jump to a line
     * beyond the HLT command.
     * @param address The line of code to be jumped to, which is equal to the
     *                program address minus 1
     * @return Always {@code Status.OK}
     */
    public Status jmp(int address) {
        return this.performBranch(address, Condition.NN);
    }

    /**
     * This method sets the program counter to the given program address minus 1, if
     * the value inside the accumulator is equal to 0. Otherwise, the program counter
     * will be incremented.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code JEZ}.
     * Note that the program is trapped in an infinite loop if you jump to a line
     * beyond the HLT command.
     * @param address The line of code to be jumped to, which is equal to the
     *                program address minus 1
     * @return Always {@code Status.OK}
     */
    public Status jez(int address) {
        return this.performBranch(address, Condition.EZ);
    }

    /**
     * This method sets the program counter to the given program address minus 1, if
     * the value inside the accumulator is not equal to 0. Otherwise, the program counter
     * will be incremented.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code JNE}.
     * Note that the program is trapped in an infinite loop if you jump to a line
     * beyond the HLT command.
     * @param address The line of code to be jumped to, which is equal to the
     *                program address minus 1
     * @return Always {@code Status.OK}
     */
    public Status jne(int address) {
        return this.performBranch(address, Condition.NE);
    }

    /**
     * This method sets the program counter to the given program address minus 1, if
     * the value inside the accumulator is less than 0. Otherwise, the program counter
     * will be incremented.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code JLZ}.
     * Note that the program is trapped in an infinite loop if you jump to a line
     * beyond the HLT command.
     * @param address The line of code to be jumped to, which is equal to the
     *                program address minus 1
     * @return Always {@code Status.OK}
     */
    public Status jlz(int address) {
        return this.performBranch(address, Condition.LZ);
    }

    /**
     * This method sets the program counter to the given program address minus 1, if
     * the value inside the accumulator is less than or equal to 0. Otherwise, the
     * program counter will be incremented.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code JLE}.
     * Note that the program is trapped in an infinite loop if you jump to a line
     * beyond the HLT command.
     * @param address The line of code to be jumped to, which is equal to the
     *                program address minus 1
     * @return Always {@code Status.OK}
     */
    public Status jle(int address) {
        return this.performBranch(address, Condition.LE);
    }

    /**
     * This method sets the program counter to the given program address minus 1, if
     * the value inside the accumulator is greater than 0. Otherwise, the program counter
     * will be incremented.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code JGZ}.
     * Note that the program is trapped in an infinite loop if you jump to a line
     * beyond the HLT command.
     * @param address The line of code to be jumped to, which is equal to the
     *                program address minus 1
     * @return Always {@code Status.OK}
     */
    public Status jgz(int address) {
        return this.performBranch(address, Condition.GZ);
    }

    /**
     * This method sets the program counter to the given program address minus 1, if
     * the value inside the accumulator is greater than or equal to 0. Otherwise, the
     * program counter will be incremented.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code JGE}.
     * Note that the program is trapped in an infinite loop if you jump to a line
     * beyond the HLT command.
     * @param address The line of code to be jumped to, which is equal to the
     *                program address minus 1
     * @return Always {@code Status.OK}
     */
    public Status jge(int address) {
        return this.performBranch(address, Condition.GE);
    }

    /**
     * This method marks the end of a program.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command accessible by the user through the keyword {@code HLT}.
     * It is necessary at the end of every program.
     * @param value Unused, only for conformity purposes
     * @return {@code Always Status.FINISH_SUCCESS}
     */
    public Status hlt(int value) {
        return Status.FINISH_SUCCESS;
    }
}

// TODO: For synchronized methods, say why they are synchronized
// TODO: Add appropriate docs for constructors
// TODO: Format all files
// TODO: Sort methods
// TODO: Rename "PrinterThread" to "MessagePrinter" and use 'aka', like with RunnerThread and RunUsecase
// TODO: Adjust other tests to test all methods
// TODO: Inside accumulator -> In accuumulator
// TODO: Adjust "Jump beyond"
