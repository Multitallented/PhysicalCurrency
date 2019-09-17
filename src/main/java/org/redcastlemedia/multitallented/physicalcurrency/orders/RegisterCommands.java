package org.redcastlemedia.multitallented.physicalcurrency.orders;

import java.util.HashMap;

import org.redcastlemedia.multitallented.physicalcurrency.commands.Generic;
import org.redcastlemedia.multitallented.physicalcurrency.commands.PCurrCommand;

public final class RegisterCommands {
    private RegisterCommands() {

    }
    public static HashMap<String, PCurrCommand> execute() {
        HashMap<String, PCurrCommand> commands = new HashMap<>();
        commands.put("pay", null); // TODO make paycommand
        Generic generic = new Generic();
        commands.put("pcurr", generic);
        commands.put("money", generic);
        commands.put("bal", generic);
        return commands;
    }
}
