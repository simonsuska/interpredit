package de.example.domain.entities.machines;

/** A type which decodes string. */
public interface Decoder {
    Command decode(String command);
}
