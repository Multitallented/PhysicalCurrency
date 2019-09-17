package org.redcastlemedia.multitallented.physicalcurrency.orders;

import java.util.HashMap;

import org.redcastlemedia.multitallented.physicalcurrency.commands.Generic;
import org.redcastlemedia.multitallented.physicalcurrency.commands.PCurrCommand;
import org.redcastlemedia.multitallented.physicalcurrency.commands.Pay;
import org.redcastlemedia.multitallented.physicalcurrency.commands.Reload;

public final class RegisterCommands {
    private RegisterCommands() {

    }
    public static HashMap<String, PCurrCommand> execute() {
        HashMap<String, PCurrCommand> commands = new HashMap<>();
        commands.put("pay", new Pay());
        Generic generic = new Generic();
        commands.put("pcurr", generic);
        commands.put("money", generic);
        commands.put("bal", generic);
        commands.put("reload", new Reload());
        return commands;
    }
}
