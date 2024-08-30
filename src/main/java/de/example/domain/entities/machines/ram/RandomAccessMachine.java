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
     * The runner thread uses this one to access the proper line of code.
     * <br><br>
     * The program counter is in the value range from 0 to the number of lines of code in the editor minus 1. This is
     * because it simplifies the access to the list containing the coded commands. However, the user uses a value range
     * from 1 to the number of lines of code in the editor, as this is more intuitive. For this reason, the program
     * counter is set to the address minus 1 for all commands in which the user uses a program address.
     */
    private int pc;

    /**
     * This attribute represents the internal memory of the random access machine including the accumulator, which is
     * located at index 0 and inaccessible to the user.
     */
    private int[] memory;

    /**
     * This attribute is used to receive an input from or provide an output to the user.
     */
    private final Buffer<String> buffer;

    /** This attribute is used to decode random access machine commands. */
    private final Decoder decoder;

    /**
     * This attribute is used to check whether the user has already been informed to enter a value before the program
     * waits for it.
     */
    private boolean notifiedAboutInput = false;

    //: SECTION: - CONSTRUCTORS

    /** This constructor creates a new random access machine. */
    @Inject
    public RandomAccessMachine(
            @Named(Di.RAM_STRING_BUFFER) Buffer<String> buffer,
            @Named(Di.RAM_DECODER) Decoder decoder) {
        this.buffer = Objects.requireNonNull(buffer);
        this.decoder = Objects.requireNonNull(decoder);
    }

    /** This constructor creates a new random access machine and is only used for testing purposes. */
    public RandomAccessMachine(int[] memory, Buffer<String> buffer, Decoder decoder) {
        this.memory = memory;
        this.buffer = buffer;
        this.decoder = decoder;
    }

    //: SECTION: - METHODS

    /** This method increments the program counter. */
    private void forward() {
        this.pc++;
    }

    /**
     * This method sets the program counter to the given program address.
     *
     * <br><br><b>Discussion</b><br>
     * Since the given program address is user-generated, it is decremented before storing it in the program counter.
     *
     * @param address The program address to be set
     * @see RandomAccessMachine#pc
     */
    private void branch(int address) {
        this.pc = address - 1;
    }

    /**
     * This method checks if the given memory address is within the memory accessible to the user, which is in the
     * interval between 1 and the set value, including the limits.
     *
     * @param address The memory address to be checked
     * @return {@code true} if the memory address is within the memory accessible to the user, otherwise {@code false}
     */
    private boolean isMemoryAddressWithinBounds(int address) {
        return address > 0 && address < this.memory.length;
    }

    /**
     * This method causes the given arithmetic operation to be executed.
     *
     * <br><br><b>Discussion</b><br>
     * More concrete, the value behind the given memory address will be offset against the value in the accumulator.
     * The result is stored in the accumulator, which means that the previous value is overwritten.
     *
     * @param address The memory address containing the value which is offset against the value in the accumulator
     * @param arithmetic The arithmetic operation to be executed
     * @return {@code OK}, if the memory address is within the accessible memory, otherwise {@code MEMORY_ADDRESS_ERROR}.
     *         If the arithmetic operations is a division, the memory address must not contain a value that is equal to
     *         0, otherwise a {@code DIVISION_BY_ZERO_ERROR} is returned.
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
     * More concrete, the program counter will be set to the given program address minus 1 if the condition is met.
     * Otherwise, it will be incremented.
     *
     * @param address The line of code to be jumped to, which is equal to the program address minus 1
     * @param condition The condition to be met
     * @return Always {@code OK}
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
     * This method interrupts the machine and closes the buffer.
     *
     * <br><br><b>Discussion</b><br>
     * In this regard, closing the buffer is crucial to wake up the runner thread from the blocking state.
     */
    @Override
    public void interrupt() {
        super.interrupt();
        this.buffer.close();
    }

    /** This method resets the machine, which includes the program counter, the memory and the buffer. */
    @Override
    public void reset() {
        super.reset();

        this.pc = 0;
        this.memory = null;
        this.buffer.reset();
    }

    /**
     * This method returns the current program counter in the value range from 0 to the number of lines of code in
     * the editor minus 1.
     *
     * <br><br><b>Discussion</b><br>
     * In the context of Interpredit, it is used by the runner thread to access the proper line of code while the
     * program is executed.
     *
     * @return The current program counter
     */
    @Override
    public int getPc() {
        return this.pc;
    }

    /**
     * This method decodes the given command and subsequently executes it on this machine.
     *
     * @param cmd The encoded command to be executed
     * @return {@code DECODE_ERROR}, if the command could not be decoded, otherwise the status of the corresponding
     *         command
     */
    @Override
    public Status run(String cmd) {
        RandomAccessMachineCommand command = (RandomAccessMachineCommand) this.decoder.decode(cmd);
        return command == null ? Status.DECODE_ERROR : command.execute(this);
    }

    /**
     * This method requests an output from the machine by reading the buffer.
     *
     * <br><br><b>Discussion</b><br>
     * Note that this method is blocking, which means it blocks the corresponding thread as long as the buffer is empty.
     * It only continues when the buffer has either been filled or closed.
     * <br><br>
     * In the context of Interpredit, this method is only used by the printer thread to receive the output after an OUT
     * command and then output it to the console.
     *
     * @return The requested output or {@code null}, if the buffer was closed.
     */
    @Override
    public String requestOutput() {
        String value = "";

        try { value = this.buffer.read(); }
        catch (InterruptedException _) {}

        return value;
    }

    /**
     * This method delivers the given string as an input to the machine.
     *
     * <br><br><b>Discussion</b><br>
     * This method is only called from the main thread when the user hits <i>Enter</i> after an input. The evaluation of
     * the input does not yet take place here.
     *
     * @param input The input to be delivered
     * @return {@code true} if the input is not {@code null}, otherwise {@code false}
     */
    @Override
    public boolean deliverInput(String input) {
        if (input != null) {
            this.buffer.write(input);
            return true;
        }

        // If the input is `null`, close the buffer to notify
        // the runner thread to prevent it from being stuck.
        this.buffer.close();
        return false;
    }

    /**
     * This method initializes the memory (the tape) with the given number of fields and must be the first command in
     * every program. All fields are initialized with the number 0.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword SET.
     *
     * @param value The number of fields of the memory accessible to the user
     * @return {@code OK} if the memory was successfully initialized, otherwise {@code SET_ERROR}
     */
    public Status set(int value) {
        if (value < 0 || value > 100)
            return Status.SET_ERROR;

        this.memory = new int[value+1];
        this.forward();
        return Status.OK;
    }

    /**
     * This method increments the program counter.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can not access. It is called implicitly when the user enters a line that
     * does not contain any text. The command causes the line to be skipped.
     *
     * @param value Unused, only for conformity purposes
     * @return Always {@code OK}
     */
    public Status hop(int value) {
        this.forward();
        return Status.OK;
    }

    /**
     * This method adds the value stored in the given memory address to the value currently stored in the accumulator.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword ADD.
     *
     * @param address The memory address containing the value that is added to the value in the accumulator
     * @return {@code OK} if the memory address is within the accessible memory, otherwise {@code MEMORY_ADDRESS_ERROR}
     */
    public Status add(int address) {
        return this.performArithmetic(address, Arithmetic.ADD);
    }

    /**
     * This method subtracts the value stored in the given memory address from the value currently stored in the
     * accumulator.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword SUB.
     *
     * @param address The memory address containing the value that is subtracted from the value in the accumulator
     * @return {@code OK} if the memory address is within the accessible memory, otherwise {@code MEMORY_ADDRESS_ERROR}
     */
    public Status sub(int address) {
        return this.performArithmetic(address, Arithmetic.SUB);
    }

    /**
     * This method multiplies the value stored in the given memory address to the value currently stored in the
     * accumulator.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword MUL.
     *
     * @param address The memory address containing the value that is multiplied to the value in the accumulator
     * @return {@code OK} if the memory address is within the accessible memory, otherwise {@code MEMORY_ADDRESS_ERROR}
     */
    public Status mul(int address) {
        return this.performArithmetic(address, Arithmetic.MUL);
    }

    /**
     * This method divides the value stored in the given memory address from the value currently stored in the
     * accumulator. Any resulting decimal places are cut off.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword DIV.
     *
     * @param address The memory address containing the value that is divided from the value in the accumulator
     * @return {@code OK} if the memory address is within the accessible memory and contains a value that is not 0,
     *         otherwise {@code MEMORY_ADDRESS_ERROR} or {@code DIVISION_BY_ZERO_ERROR}
     */
    public Status div(int address) {
        return this.performArithmetic(address, Arithmetic.DIV);
    }

    /**
     * This method loads the value stored in the given memory address into the accumulator and thus overrides the
     * currently stored value in the accumulator.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword LDA.
     *
     * @param address The memory address containing the value that is loaded into the accumulator
     * @return {@code OK} if the memory address is within the accessible memory, otherwise {@code MEMORY_ADDRESS_ERROR}
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
     * This method loads the given value into the accumulator and thus overrides the currently stored value in the
     * accumulator.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword LDK.
     *
     * @param value The value that is loaded into the accumulator
     * @return Always {@code OK}
     */
    public Status ldk(int value) {
        this.memory[0] = value;
        this.forward();
        return Status.OK;
    }

    /**
     * This method stores the currently stored value in the accumulator in the given memory address.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword STA.
     *
     * @param address The memory address the value in the accumulator is stored in
     * @return {@code OK} if the memory address is within the accessible memory, otherwise {@code MEMORY_ADDRESS_ERROR}
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
     * This method stores the user input in the given memory address. The program execution is paused until a user
     * input is made.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword INP. Note that this method is blocking, which
     * means it blocks the corresponding thread as long as the buffer is empty. It only continues when the buffer has
     * either been filled or closed. Furthermore, this method is invoked twice if the buffer is empty. In the first
     * invocation, {@code INPUT} is returned to inform the user that he has to enter a value. The second invocation
     * then waits blocking for a specific input.
     *
     * @param address The memory address the user input is stored in
     * @return
     * <ul>
     *  <li>
     *      {@code MEMORY_ADDRESS_ERROR} if the given memory address
     *      is not within the accessible memory.
     *  </li>
     *  <li>
     *      {@code INPUT_ERROR} if the delivered user input is invalid,
     *      which means it is not an integer.
     *  </li>
     *  <li>
     *      {@code INPUT} if the buffer is empty and the user has not
     *      yet been notified to enter an input.
     *  </li>
     *  <li>
     *      {@code OK} if the buffer contains a value which is an integer
     *  </li>
     * </ul>
     */
    public Status inp(int address) {
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
     * This method outputs the value stored in the given memory address on the console.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword OUT. This method tells the printer thread
     * that an output is to be made.
     *
     * @param address The memory address containing the value that is output on the console
     * @return {@code OK} if the memory address is within the accessible memory, otherwise {@code MEMORY_ADDRESS_ERROR}
     */
    public Status out(int address) {
        if (this.isMemoryAddressWithinBounds(address)) {
            this.buffer.write(String.valueOf(this.memory[address]));
            this.forward();
            return Status.OUTPUT;
        }

        return Status.MEMORY_ADDRESS_ERROR;
    }

    /**
     * This method causes the program execution to continue in the given line of code, which is the program counter
     * minus 1. The program is trapped in an infinite loop if the target line of code is beyond the SET or HLT command.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword JMP.
     *
     * @param address The line of code to be jumped to, which is equal to the program address minus 1
     * @return Always {@code OK}
     */
    public Status jmp(int address) {
        return this.performBranch(address, Condition.NN);
    }

    /**
     * This method causes the program execution to continue in the given line of code, if the currently stored value
     * in the accumulator is equal to 0. Otherwise, the program execution continues in the next line of code. The
     * program is trapped in an infinite loop if the target line of code is beyond the SET or HLT command.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword JEZ.
     *
     * @param address The line of code to be jumped to, which is equal to the program address minus 1
     * @return Always {@code OK}
     */
    public Status jez(int address) {
        return this.performBranch(address, Condition.EZ);
    }

    /**
     * This method causes the program execution to continue in the given line of code, if the currently stored value
     * in the accumulator is not equal to 0. Otherwise, the program execution continues in the next line of code. The
     * program is trapped in an infinite loop if the target line of code is beyond the SET or HLT command.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword JNE.
     *
     * @param address The line of code to be jumped to, which is equal to the program address minus 1
     * @return Always {@code OK}
     */
    public Status jne(int address) {
        return this.performBranch(address, Condition.NE);
    }

    /**
     * This method causes the program execution to continue in the given line of code, if the currently stored value
     * in the accumulator is less than 0. Otherwise, the program execution continues in the next line of code. The
     * program is trapped in an infinite loop if the target line of code is beyond the SET or HLT command.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword JLZ.
     *
     * @param address The line of code to be jumped to, which is equal to the program address minus 1
     * @return Always {@code OK}
     */
    public Status jlz(int address) {
        return this.performBranch(address, Condition.LZ);
    }

    /**
     * This method causes the program execution to continue in the given line of code, if the currently stored value
     * in the accumulator is less than or equal to 0. Otherwise, the program execution continues in the next line of
     * code. The program is trapped in an infinite loop if the target line of code is beyond the SET or HLT command.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword JLE.
     *
     * @param address The line of code to be jumped to, which is equal to the program address minus 1
     * @return Always {@code OK}
     */
    public Status jle(int address) {
        return this.performBranch(address, Condition.LE);
    }

    /**
     * This method causes the program execution to continue in the given line of code, if the currently stored value in
     * the accumulator is greater than 0. Otherwise, the program execution continues in the next line of code. The
     * program is trapped in an infinite loop if the target line of code is beyond the SET or HLT command.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword JGZ.
     *
     * @param address The line of code to be jumped to, which is equal to the program address minus 1
     * @return Always {@code OK}
     */
    public Status jgz(int address) {
        return this.performBranch(address, Condition.GZ);
    }

    /**
     * This method causes the program execution to continue in the given line of code, if the currently stored value
     * in the accumulator is greater than or equal to 0. Otherwise, the program execution continues in the next line
     * of code. The program is trapped in an infinite loop if the target line of code is beyond the SET or HLT command.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword JGE.
     *
     * @param address The line of code to be jumped to, which is equal to the program address minus 1
     * @return Always {@code OK}
     */
    public Status jge(int address) {
        return this.performBranch(address, Condition.GE);
    }

    /**
     * This method marks the end of a program and must be the last command in every program.
     *
     * <br><br><b>Discussion</b><br>
     * This method is a command that the user can access via the keyword HLT.
     *
     * @param value Unused, only for conformity purposes
     * @return {@code Always FINISH_SUCCESS}
     */
    public Status hlt(int value) {
        return Status.FINISH_SUCCESS;
    }
}
