package org.redcastlemedia.multitallented.physicalcurrency.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;
import org.redcastlemedia.multitallented.physicalcurrency.orders.DepositPlayer;
import org.redcastlemedia.multitallented.physicalcurrency.orders.Format;
import org.redcastlemedia.multitallented.physicalcurrency.orders.GetBalance;
import org.redcastlemedia.multitallented.physicalcurrency.orders.HasAccount;

public class Add implements PCurrCommand {
    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if (!commandSender.isOp() && (PhysicalCurrency.perm == null ||
                !PhysicalCurrency.perm.has(commandSender, "pcurr.admin"))) {
            commandSender.sendMessage(ChatColor.RED + PhysicalCurrency.getPrefix() + "Permission denied!");
            return true;
        }
        String displayName = args[1];
        double amount = 0;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (Exception e) {
            commandSender.sendMessage(ChatColor.RED + PhysicalCurrency.getPrefix() + "Invalid usage: /money sub name amount");
            return true;
        }
        if (amount < 0) {
            commandSender.sendMessage(ChatColor.RED + PhysicalCurrency.getPrefix() + "Invalid usage: /money sub name amount");
            return true;
        }
        OfflinePlayer receipt = Bukkit.getPlayer(displayName);
        if (receipt == null) {
            receipt = Bukkit.getOfflinePlayer(displayName);
            if (!HasAccount.execute(receipt)) {
                commandSender.sendMessage(ChatColor.RED + PhysicalCurrency.getPrefix() + "No player found by the name " + displayName);
                return true;
            }
        }
        DepositPlayer.execute(receipt, amount);
        commandSender.sendMessage(ChatColor.GREEN + PhysicalCurrency.getPrefix() +
                "You have added " + Format.execute(amount) + " to " + displayName + ". New balance: " +
                Format.execute(GetBalance.execute(receipt)));
        return true;
    }
}
