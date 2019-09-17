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
        if (offlinePlayer.isOnline()) {
            Player player = (Player) offlinePlayer;
            double amountTaken = TransferPhysicalToAccount.execute(player, amount);
            TransferAccountToPhysical.execute(player);
            double inventoryAmount = ItemUtil.countCurrencyInInventory(player.getInventory());
            if (amount <= amountTaken) {
                return new EconomyResponse(amount, account.getAmount() + inventoryAmount,
                        EconomyResponse.ResponseType.SUCCESS, "");
            }
        }
        double newAmount = account.getAmount() - amount;
        newAmount = newAmount < 0 ? 0 : newAmount;
        account.setAmount(newAmount);
        return new EconomyResponse(amount, newAmount,
                EconomyResponse.ResponseType.SUCCESS, "");
    }
}
