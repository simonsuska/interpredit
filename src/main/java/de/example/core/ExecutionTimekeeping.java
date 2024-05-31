package de.example.core;

import java.util.Date;

/**
 * This type provides a time keeping mechanism to
 * measure the execution time of a program.
 */
public class ExecutionTimekeeping {
    private static long start;
    private static long end;

    /** This method starts the time measurement. */
    public static void start() {
        start = new Date().getTime();
    }

    /** This method ends the time measurement. */
    public static void end() {
        end = new Date().getTime();
    }

    /**
     * This method calculates the duration between start and end in milliseconds.
     * @return The duration in milliseconds
     */
    public static long getDuration() {
        return end - start;
    }
}
