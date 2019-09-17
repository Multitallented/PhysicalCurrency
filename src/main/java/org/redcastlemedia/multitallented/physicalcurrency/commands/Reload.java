package org.redcastlemedia.multitallented.physicalcurrency.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.redcastlemedia.multitallented.physicalcurrency.ConfigManager;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;

public class Reload implements PCurrCommand {
    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if (!commandSender.isOp() && (PhysicalCurrency.perm == null ||
                !PhysicalCurrency.perm.has(commandSender, "pcurr.admin"))) {
            commandSender.sendMessage(ChatColor.RED + PhysicalCurrency.getPrefix() + "Permission denied!");
            return true;
        }
        ConfigManager.getInstance().loadConfig();
        return true;
    }
}
