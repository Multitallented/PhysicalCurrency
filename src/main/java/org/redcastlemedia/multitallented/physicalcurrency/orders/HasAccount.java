package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;

public final class HasAccount {
    private HasAccount() {

    }
    public static boolean execute(String playerName) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        if (!offlinePlayer.hasPlayedBefore()) {
            return false;
        }
        return execute(offlinePlayer);
    }
    public static boolean execute(OfflinePlayer offlinePlayer) {
        if (PhysicalCurrency.getInstance() == null) {
            return false;
        }
        return AccountManager.getInstance().hasAccount(offlinePlayer.getUniqueId());
    }
}
