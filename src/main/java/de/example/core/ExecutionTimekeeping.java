package de.example.core;

import java.util.Date;
import java.util.Locale;

/**
 * This type provides a time keeping mechanism to measure the execution time of a program.
 *
 * <br><br><b>Discussion</b><br>
 * In the context of Interpredit, this class is a passive one, as its state is accessed from multiple threads. The
 * runner thread uses the methods {@code start()} and {@code end()} and thus sets the attributes of the same name.
 * The printer thread uses the {@code getDuration()} method to obtain the execution duration, which also accesses
 * the state. For this reason, all methods are synchronized. The monitor is the type itself.
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
     * @return The duration in milliseconds formatted in the current locale
     */
    public synchronized static String getDuration() {
        return String.format(Locale.getDefault(), "%d",  end - start);
    }
}
