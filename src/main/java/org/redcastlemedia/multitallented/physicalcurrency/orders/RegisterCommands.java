package org.redcastlemedia.multitallented.physicalcurrency.orders;

import java.util.HashMap;

import org.redcastlemedia.multitallented.physicalcurrency.commands.PCurrCommand;

public final class RegisterCommands {
    private RegisterCommands() {

    }
    public static HashMap<String, PCurrCommand> execute() {
        HashMap<String, PCurrCommand> commands = new HashMap<>();
        commands.put("pay", null); // TODO make paycommand
        commands.put("pcurr", null); // TODO make pcurr command
        commands.put("money", null); // TODO make pcurr command
        commands.put("bal", null); // TODO make pcurr command
        return commands;
    }
}
