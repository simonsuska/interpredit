// Copyright (c) 2025 Simon Suska
// SPDX-License-Identifier: MIT

package de.example.domain.entities.machines;

import de.example.domain.entities.Status;

import java.util.concurrent.locks.ReentrantLock;

/**
 * A type that defines the basic functions of a machine.
 *
 * <br><br><b>Discussion</b><br>
 * In the context of Interpredit, this class is a passive one, as its state is accessed from multiple threads. The
 * runner thread uses the method {@code isInterrupt()} and thus uses the state. The main thread uses the method
 * {@code interrupt()} if the user deliberately interrupts the program execution by clicking on the corresponding
 * menu item. This also accesses the state. For this reason, all methods that access the {@code interrupted}
 * attribute are synchronized. The monitor here is the object itself.
 */
public abstract class Machine {

    //: SECTION: - ATTRIBUTES

    private final ReentrantLock lock = new ReentrantLock();

    /** This attribute stores whether the machine is interrupted or not. */
    private boolean isInterrupted = false;

    //: SECTION: - METHODS

    /** This method interrupts the machine. */
    public void interrupt() {
        lock.lock();
        this.isInterrupted = true;
        lock.unlock();
    }

    /**
     * This method checks whether the machine is interrupted or not.
     *
     * @return {@code true} if the machine is interrupted, otherwise {@code false}
     */
    public boolean isInterrupted() {
        lock.lock();
        boolean isInterrupt = this.isInterrupted;
        lock.unlock();
        return isInterrupt;
    }

    /** This method resets the machine, which is restoring the uninterrupted state. */
    public void reset() {
        lock.lock();
        isInterrupted = false;
        lock.unlock();
    }

    public abstract int getPC();
    public abstract Status run(String cmd);
    public abstract String requestOutput();
    public abstract boolean deliverInput(String input);
}
