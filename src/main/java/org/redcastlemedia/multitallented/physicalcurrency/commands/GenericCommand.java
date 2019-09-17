package org.redcastlemedia.multitallented.physicalcurrency.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;
import org.redcastlemedia.multitallented.physicalcurrency.orders.Format;
import org.redcastlemedia.multitallented.physicalcurrency.orders.GetBalance;

public class GenericCommand implements PCurrCommand {
    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if (args.length < 1 && commandSender instanceof Player) {
            commandSender.sendMessage(PhysicalCurrency.getPrefix() +
                    Format.execute(GetBalance.execute((Player) commandSender)));
            return true;
        }
        // TODO admin args
        return true;
    }
}
