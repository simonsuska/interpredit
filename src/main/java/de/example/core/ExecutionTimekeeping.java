package de.example.core;

import java.util.Date;
import java.util.Locale;

/**
 * This type provides a time keeping mechanism to
 * measure the execution time of a program.
 */
public class ExecutionTimekeeping {

    //: SECTION: - ATTRIBUTES

    private static long start;
    private static long end;

    //: SECTION: - METHODS

    /** This method starts the time measurement. */
    public synchronized static void start() {
        start = new Date().getTime();
    }

    /** This method ends the time measurement. */
    public synchronized static void end() {
        end = new Date().getTime();
    }

    /**
     * This method calculates the duration between start and end in milliseconds.
     *
     * @return The duration in milliseconds formatted in the default locale
     */
    public synchronized static String getDuration() {
        return String.format(Locale.getDefault(), "%d",  end - start);
    }
}
