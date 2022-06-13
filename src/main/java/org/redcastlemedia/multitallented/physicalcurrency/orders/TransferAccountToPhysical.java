package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.redcastlemedia.multitallented.physicalcurrency.ConfigManager;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.Account;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;
import org.redcastlemedia.multitallented.physicalcurrency.util.ItemUtil;

public final class TransferAccountToPhysical {
    private TransferAccountToPhysical() {

    }
    public static void execute(Player player) {
        if (player.getInventory().firstEmpty() < 0) {
            return;
        }
        Account account = AccountManager.getInstance().getAccount(player.getUniqueId());
        if (account.getAmount() < 1) {
            return;
        }
        ItemStack eightyOneItem = ConfigManager.getInstance().getEightyOneItem();
        final int MAX_EIGHTY_ONE_STACK_SIZE = eightyOneItem.getMaxStackSize() * 81;
        int i = 0;
        do {
            if (account.getAmount() > MAX_EIGHTY_ONE_STACK_SIZE) {
                ItemStack eightyOneClone = ItemUtil.processItem(
                        ConfigManager.getInstance().getEightyOneMaterialString(),
                        ConfigManager.getInstance().getEightyOneName(),
                        ConfigManager.getInstance().getEightyOneLore());
                eightyOneClone.setAmount(eightyOneItem.getMaxStackSize());
                player.getInventory().addItem(eightyOneClone);
                account.setAmount(account.getAmount() - (eightyOneClone.getAmount() * 81));
            } else if (account.getAmount() >= 81) {
                ItemStack eightyOneClone = ItemUtil.processItem(
                        ConfigManager.getInstance().getEightyOneMaterialString(),
                        ConfigManager.getInstance().getEightyOneName(),
                        ConfigManager.getInstance().getEightyOneLore());
                int newAmount = (int) Math.floor(account.getAmount() / 81);
                newAmount = Math.min(newAmount, eightyOneClone.getMaxStackSize());
                eightyOneClone.setAmount(newAmount);
                if (addItemToNonHotbar(player, eightyOneClone)) {
                    player.getInventory().addItem(eightyOneClone);
                }
                account.setAmount(account.getAmount() - (newAmount * 81));
            } else if (account.getAmount() >= 9) {
                ItemStack nineClone = ItemUtil.processItem(
                        ConfigManager.getInstance().getNineMaterialString(),
                        ConfigManager.getInstance().getNineName(),
                        ConfigManager.getInstance().getNineLore());
                int newAmount = (int) Math.floor(account.getAmount() / 9);
                newAmount = Math.min(newAmount, nineClone.getMaxStackSize());
                nineClone.setAmount(newAmount);
                if (addItemToNonHotbar(player, nineClone)) {
                    player.getInventory().addItem(nineClone);
                }
                account.setAmount(account.getAmount() - (newAmount * 9));
            } else {
                ItemStack singleClone = ItemUtil.processItem(
                        ConfigManager.getInstance().getSingleMaterialString(),
                        ConfigManager.getInstance().getSingleName(),
                        ConfigManager.getInstance().getSingleLore());
                int newAmount = (int) Math.floor(account.getAmount());
                newAmount = Math.min(newAmount, singleClone.getMaxStackSize());
                singleClone.setAmount(newAmount);
                if (addItemToNonHotbar(player, singleClone)) {
                    player.getInventory().addItem(singleClone);
                }
                account.setAmount(account.getAmount() - newAmount);
            }
            i++;
        } while (i < 27 && player.getInventory().firstEmpty() > -1 && account.getAmount() >= 1);
    }

    private static boolean addItemToNonHotbar(Player player, ItemStack itemStack) {
        for (int i = 9; i< player.getInventory().getSize(); i++) {
            ItemStack itemStack1 = player.getInventory().getItem(i);
            if (itemStack1 == null || itemStack1.getType() == Material.AIR) {
                player.getInventory().setItem(i, itemStack);
                return false;
            }
        }
        return true;
    }
}
