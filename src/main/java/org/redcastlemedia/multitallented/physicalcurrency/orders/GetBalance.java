package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.Account;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;
import org.redcastlemedia.multitallented.physicalcurrency.util.ItemUtil;

public final class GetBalance {
    private GetBalance() {

    }
    public static double execute(OfflinePlayer offlinePlayer) {
        Account account = AccountManager.getInstance().getAccount(offlinePlayer.getUniqueId());
        double balance = account.getAmount();
        if (offlinePlayer.isOnline()) {
            double physicalCurrencyValue = ItemUtil.countCurrencyInInventory(((Player) offlinePlayer).getInventory());
            balance += physicalCurrencyValue;
        }
        return balance;
    }
}
