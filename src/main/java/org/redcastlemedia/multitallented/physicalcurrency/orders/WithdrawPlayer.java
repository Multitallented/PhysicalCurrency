package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.Account;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;

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
        double newAmount = account.getAmount() - amount;
        newAmount = newAmount < 0 ? 0 : newAmount;
        account.setAmount(newAmount);
        // TODO withdraw physical currency
        return new EconomyResponse(amount, newAmount,
                EconomyResponse.ResponseType.SUCCESS, "");
    }
}
