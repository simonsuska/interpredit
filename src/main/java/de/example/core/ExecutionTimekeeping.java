// Copyright (c) 2025 Simon Suska
// SPDX-License-Identifier: MIT

package de.example.core;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This type provides a time keeping mechanism to measure the execution time of a program.
 *
 * <br><br><b>Discussion</b><br>
 * In the context of Interpredit, this class is a passive one, as its state is accessed from multiple threads. The
 * runner thread uses the methods {@code start()} and {@code end()} and thus sets the attributes of the same name.
 * The printer thread uses the {@code getDuration()} method to obtain the execution duration, which also accesses
 * the state. For this reason, all methods <s>are synchronized. The monitor is the type itself.</s> The legacy
 * synchronized implementation has been updated with locks.
 */
public class ExecutionTimekeeping {

    //: SECTION: - ATTRIBUTES

    private static final ReentrantLock lock = new ReentrantLock();

    private static long start;
    private static long end;

    //: SECTION: - METHODS

    /** This method starts the time measurement. */
    public static void start() {
        lock.lock();
        start = new Date().getTime();
        lock.unlock();
    }

    /** This method ends the time measurement. */
    public static void end() {
        lock.lock();
        end = new Date().getTime();
        lock.unlock();
    }

    /**
     * This method calculates the duration between start and end in milliseconds.
     *
     * @return The duration in milliseconds formatted in the current locale
     */
    public static String getDuration() {
        lock.lock();
        long duration = end - start;
        lock.unlock();
        return String.format(Locale.getDefault(), "%d",  duration);
    }
}
