package org.redcastlemedia.multitallented.physicalcurrency.orders;

import java.util.HashMap;

import org.redcastlemedia.multitallented.physicalcurrency.commands.GenericCommand;
import org.redcastlemedia.multitallented.physicalcurrency.commands.PCurrCommand;

public final class RegisterCommands {
    private RegisterCommands() {

    }
    public static HashMap<String, PCurrCommand> execute() {
        HashMap<String, PCurrCommand> commands = new HashMap<>();
        commands.put("pay", null); // TODO make paycommand
        GenericCommand genericCommand = new GenericCommand();
        commands.put("pcurr", genericCommand);
        commands.put("money", genericCommand);
        commands.put("bal", genericCommand);
        return commands;
    }
}
