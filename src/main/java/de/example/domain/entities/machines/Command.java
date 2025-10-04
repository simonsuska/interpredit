// Copyright (c) 2025 Simon Suska
// SPDX-License-Identifier: MIT

package de.example.domain.entities.machines;

import de.example.domain.entities.Status;

/** A type that provides the ability to execute a command on a machine. */
public interface Command {
    Status execute(Machine machine);
}
