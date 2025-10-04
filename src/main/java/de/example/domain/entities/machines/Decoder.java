// Copyright (c) 2025 Simon Suska
// SPDX-License-Identifier: MIT

package de.example.domain.entities.machines;

/** A type that decodes a string. */
public interface Decoder {
    Command decode(String command);
}
