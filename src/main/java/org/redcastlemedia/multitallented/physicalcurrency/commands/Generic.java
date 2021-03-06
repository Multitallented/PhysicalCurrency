package org.redcastlemedia.multitallented.physicalcurrency.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;
import org.redcastlemedia.multitallented.physicalcurrency.orders.Format;
import org.redcastlemedia.multitallented.physicalcurrency.orders.GetBalance;

public class Generic implements PCurrCommand {
    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if (args.length < 1 && commandSender instanceof Player) {
            commandSender.sendMessage(ChatColor.GREEN + PhysicalCurrency.getPrefix() +
                    Format.execute(GetBalance.execute((Player) commandSender)));
            return true;
        }
        if (args.length < 1) {
            return true;
        }
        if (args[0].equalsIgnoreCase("set") && args.length > 2) {
            new Set().execute(commandSender, args);
            return true;
        }
        if ((args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("add")) &&
                args.length > 2) {
            new Add().execute(commandSender, args);
        }
        if ((args[0].equalsIgnoreCase("take") || args[0].equalsIgnoreCase("sub") ||
                args[0].equalsIgnoreCase("subtract")) && args.length > 2) {
            new Subtract().execute(commandSender, args);
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        if (offlinePlayer.hasPlayedBefore()) {
            commandSender.sendMessage(ChatColor.GREEN + PhysicalCurrency.getPrefix() +
                    Format.execute(GetBalance.execute(offlinePlayer)));
        }
        return true;
    }
}
