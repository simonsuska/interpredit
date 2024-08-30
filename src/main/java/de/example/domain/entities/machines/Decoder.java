package de.example.domain.entities.machines;

/** A type that decodes a string. */
public interface Decoder {
    Command decode(String command);
}
