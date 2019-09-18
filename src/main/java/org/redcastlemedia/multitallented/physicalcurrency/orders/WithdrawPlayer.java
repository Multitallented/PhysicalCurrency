package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.Account;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;
import org.redcastlemedia.multitallented.physicalcurrency.util.ItemUtil;

import net.milkbowl.vault.economy.EconomyResponse;

public final class WithdrawPlayer {
    private WithdrawPlayer() {

    }
    public static EconomyResponse execute(String s, double amount) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(s);
        return execute(offlinePlayer, amount);
    }
    public static EconomyResponse execute(OfflinePlayer offlinePlayer, double amount) {
        Account account = AccountManager.getInstance().getAccount(offlinePlayer.getUniqueId());
        double inventoryAmount = 0;
        System.out.println(account.getAmount() + ":" + amount);
        if (offlinePlayer.isOnline() && amount > account.getAmount()) {
            Player player = (Player) offlinePlayer;
            TransferPhysicalToAccount.execute(player, amount);
            System.out.println(account.getAmount() + ":" + ItemUtil.countCurrencyInInventory(player.getInventory()));
        }
        double newAmount = account.getAmount() - amount;
        newAmount = newAmount < 0 ? 0 : newAmount;
        account.setAmount(newAmount);
        if (offlinePlayer.isOnline() && newAmount > 0) {
            Player player = (Player) offlinePlayer;
            TransferAccountToPhysical.execute(player);
            inventoryAmount += ItemUtil.countCurrencyInInventory(player.getInventory());
        }
        System.out.println(inventoryAmount + ":" + newAmount + ":" + account.getAmount());
        return new EconomyResponse(amount, newAmount + inventoryAmount,
                EconomyResponse.ResponseType.SUCCESS, "");
    }
}
