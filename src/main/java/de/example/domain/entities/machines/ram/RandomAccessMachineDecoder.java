package de.example.domain.entities.machines.ram;

import de.example.domain.entities.Status;
import de.example.domain.entities.machines.Command;
import de.example.domain.entities.machines.Decoder;

import java.util.List;
import java.util.stream.Stream;

public class RandomAccessMachineDecoder implements Decoder {
    @Override
    public Command decode(String command) {
        if (command != null) {
            List<String> cmd = Stream.of(command.split(" "))
                    .filter(c -> !c.strip().equals(""))
                    .toList();

            if (cmd.isEmpty())
                return new RandomAccessMachineCommand(Status.HOP.name().toLowerCase(), 0);

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
