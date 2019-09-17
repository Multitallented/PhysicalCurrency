package org.redcastlemedia.multitallented.physicalcurrency.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;
import org.redcastlemedia.multitallented.physicalcurrency.orders.DepositPlayer;
import org.redcastlemedia.multitallented.physicalcurrency.orders.Format;
import org.redcastlemedia.multitallented.physicalcurrency.orders.GetBalance;
import org.redcastlemedia.multitallented.physicalcurrency.orders.HasAccount;
import org.redcastlemedia.multitallented.physicalcurrency.orders.WithdrawPlayer;

public class Pay implements PCurrCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if (!commandSender.isOp() && (PhysicalCurrency.perm == null ||
                !PhysicalCurrency.perm.has(commandSender, "pcurr.pay"))) {
            commandSender.sendMessage(ChatColor.RED + PhysicalCurrency.getPrefix() + "Permission denied!");
            return true;
        }
        if (args.length < 2) {
            commandSender.sendMessage(ChatColor.RED + PhysicalCurrency.getPrefix() + "Invalid usage: /pay name amount");
            return true;
        }
        String displayName = args[0];
        double amount = 0;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (Exception e) {
            commandSender.sendMessage(ChatColor.RED + PhysicalCurrency.getPrefix() + "Invalid usage: /pay name amount");
            return true;
        }
        if (amount < 0) {
            commandSender.sendMessage(ChatColor.RED + PhysicalCurrency.getPrefix() + "Invalid usage: /pay name amount");
            return true;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + PhysicalCurrency.getPrefix() + "You must be in game to use this command.");
            return true;
        }
        Player player = (Player) commandSender;
        if (GetBalance.execute(player) < amount) {
            commandSender.sendMessage(ChatColor.RED + PhysicalCurrency.getPrefix() + "You don't have " + Format.execute(amount));
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
        WithdrawPlayer.execute(player, amount);
        DepositPlayer.execute(receipt, amount);
        player.sendMessage(ChatColor.GREEN + PhysicalCurrency.getPrefix() +
                "You have payed " + Format.execute(amount) + " to " + displayName);
        if (receipt instanceof Player) {
            ((Player) receipt).sendMessage(ChatColor.GREEN + PhysicalCurrency.getPrefix() +
                    player.getDisplayName() + " payed you " + Format.execute(amount));
        }
        return true;
    }
}
