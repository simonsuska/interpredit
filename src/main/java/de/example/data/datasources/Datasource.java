// Copyright (c) 2025 Simon Suska
// SPDX-License-Identifier: MIT

package de.example.data.datasources;

/** A type which provides read and write access to a static datasource.  */
public interface Datasource {
    boolean write(String content);
    String read();
}
