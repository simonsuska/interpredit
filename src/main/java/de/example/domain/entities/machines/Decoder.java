package de.example.domain.entities.machines;

import de.example.core.exceptions.CommandExecutionException;

public interface Decoder {
    Command decode(String command) throws CommandExecutionException;
}
