package de.example.domain.entities;

/**
 * This enumeration contains all statuses that are returned by the executing methods of the random access machine,
 * e.g. ADD, JMP, during program execution.
 */
public enum Status {
    /**
     * This status indicates that a command has been executed normally. This status is returned by most commands after
     * they have been successfully executed.
     */
    OK,

    /** This status indicates that the buffer contains a value to be printed and is only returned by the OUT command. */
    OUTPUT,

    /** This status indicates that the program is waiting for user input and is only returned by the INP command. */
    INPUT,

    /**
     * This status indicates that the memory has been initialized with an invalid number of fields. Invalid means
     * either negative or more than 100. It is only used by the SET command.
     */
    SET_ERROR,

    /**
     * This status indicates that a command has accessed an invalid memory address. It is used by all commands that
     * expect an address as an operand, e.g. ADD, LDA, INP.
     */
    MEMORY_ADDRESS_ERROR,

    /**
     * This status indicates that the program contains an invalid command. Invalid commands are those that do not
     * appear as a method of the random access machine and therefore cannot be called using reflection. The only
     * exception here is the HOP command, which is also classified as invalid if it is user-generated.
     */
    COMMAND_ERROR,

    /**
     * This status indicates that the program has an operator that is not an integer or that the number of command
     * components is not equal to 2.
     */
    DECODE_ERROR,

    /** This status indicates that the program contains a division by zero. */
    DIVISION_BY_ZERO_ERROR,

    /**
     * This status indicates that the user input is invalid. Invalid user inputs are those that cannot be parsed into
     * an integer.
     */
    INPUT_ERROR,

    /** This status indicates that the program has been finished successfully and is returned by the HLT command. */
    FINISH_SUCCESS,

    /**
     * This status indicates that the program has not been finished successfully and is passed from the runner thread
     * to the printer thread if the program execution was interrupted by the user.
     */
    FINISH_FAILURE
}
