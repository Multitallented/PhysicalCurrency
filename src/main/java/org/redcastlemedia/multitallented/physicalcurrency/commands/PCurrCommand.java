package org.redcastlemedia.multitallented.physicalcurrency.commands;

import org.bukkit.command.CommandSender;

public interface PCurrCommand {
    boolean execute(CommandSender commandSender, String[] args);
}
