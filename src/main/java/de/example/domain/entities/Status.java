package de.example.domain.entities;

/**
 * This enumeration contains status information that is returned by the
 * executing methods during the execution of the program.
 */
public enum Status {
    /** This status indicates that a command has been executed normally. */
    OK,

    /** This status indicates that the buffer contains a value to be printed. */
    OUTPUT,

    /** This status indicates that the program is waiting for user input. */
    INPUT,

    /**
     * This status indicates that the memory has been initialized
     * with an invalid amount of fields.
     */
    SET_ERROR,

    /** This status indicates that a command has accessed an invalid memory address. */
    MEMORY_ADDRESS_ERROR,

    /** This status indicates that the program contains an invalid command. */
    COMMAND_ERROR,

    /** This status indicates that the program contains an invalid operator or
     * and invalid amount of command components. */
    DECODE_ERROR,

    /** This status indicates that the program contains a division by zero. */
    DIVISION_BY_ZERO_ERROR,

    /** This status indicates that the user input is invalid. */
    INPUT_ERROR,

    /** This status indicates that the program has been finished successfully. */
    FINISH_SUCCESS,

    /** This status indicates that the program has not been finished successfully. */
    FINISH_FAILURE
}
