package de.example.domain.entities.machines.ram;

import de.example.domain.entities.machines.Command;
import de.example.domain.entities.machines.Decoder;
import java.util.List;
import java.util.stream.Stream;

/** This type decodes a random access machine command. */
public class RandomAccessMachineDecoder implements Decoder {
    /**
     * This method decodes a random access machine command from the given string.
     *
     * <br><br><b>Discussion</b><br>
     * Decoding, in this regard, means checking the amount of components and whether the operator is an integer.
     *
     * @param command The string to be decoded
     * @return The decoded random access machine command or {@code null}, if the string could not be decoded successfully.
     */
    @Override
    public Command decode(String command) {
        if (command != null) {
            command = command.replace("\t", "");

            List<String> cmd = Stream.of(command.split(" "))
                    .filter(c -> !c.isBlank())
                    .toList();

            if (cmd.isEmpty()) {
                return new RandomAccessMachineCommand("hop", 0, false);
            }

            // Every random access machine command consists of two components:
            // the command and the operator, like 'HLT 0'.
            if (cmd.size() != 2)
                return null;

            try {
                int operand = Integer.parseInt(cmd.get(1));
                return new RandomAccessMachineCommand(cmd.get(0), operand);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        return null;
    }
}
