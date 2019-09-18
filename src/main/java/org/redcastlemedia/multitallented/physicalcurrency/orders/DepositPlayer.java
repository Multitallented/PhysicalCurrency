package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.Account;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;

import net.milkbowl.vault.economy.EconomyResponse;

public final class DepositPlayer {
    private DepositPlayer() {

    }
    public static EconomyResponse execute(String name, double amount) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        return execute(offlinePlayer, amount);
    }
    public static EconomyResponse execute(OfflinePlayer offlinePlayer, double amount) {
        Account account = AccountManager.getInstance().getAccount(offlinePlayer.getUniqueId());
        double newAmount = account.getAmount() + amount;
        account.setAmount(newAmount);
        if (offlinePlayer.isOnline()) {
            Player player = (Player) offlinePlayer;
            TransferAccountToPhysical.execute(player);
        }
        return new EconomyResponse(amount, newAmount,
                EconomyResponse.ResponseType.SUCCESS, "");
    }
}
