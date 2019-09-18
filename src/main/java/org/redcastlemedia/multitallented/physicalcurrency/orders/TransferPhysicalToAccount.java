package org.redcastlemedia.multitallented.physicalcurrency.orders;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.redcastlemedia.multitallented.physicalcurrency.ConfigManager;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.Account;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;
import org.redcastlemedia.multitallented.physicalcurrency.util.ItemUtil;

public final class TransferPhysicalToAccount {
    private TransferPhysicalToAccount() {

    }
    public static double execute(Player player, double amount) {
        ItemStack singleItem = ConfigManager.getInstance().getSingleItem();
        ItemStack nineItem = ConfigManager.getInstance().getNineItem();
        ItemStack eightOneItem = ConfigManager.getInstance().getEightyOneItem();
        Account account = AccountManager.getInstance().getAccount(player.getUniqueId());

        double amountTaken = 0;
        ArrayList<ItemStack> removeThese = new ArrayList<>();
        for (ItemStack itemStack : player.getInventory()) {
            if (amountTaken >= amount) {
                break;
            }
            if (itemStack == null || (itemStack.getType() != singleItem.getType() &&
                    itemStack.getType() != nineItem.getType() &&
                    itemStack.getType() != eightOneItem.getType())) {
                continue;
            }
            if (ItemUtil.isEquivalentItem(itemStack, singleItem)) {
                amountTaken += addStackToAmount(itemStack, amount, amountTaken, 1, removeThese);
            } else if (ItemUtil.isEquivalentItem(itemStack, nineItem)) {
                amountTaken += addStackToAmount(itemStack, amount, amountTaken, 9, removeThese);
            } else if (ItemUtil.isEquivalentItem(itemStack, eightOneItem)) {
                amountTaken += addStackToAmount(itemStack, amount, amountTaken, 81, removeThese);
            }
        }
        if (!removeThese.isEmpty()) {
            ItemStack[] tempList = new ItemStack[removeThese.size()];
            player.getInventory().removeItem(removeThese.toArray(tempList));
        }
        account.setAmount(account.getAmount() + amountTaken);
        return amountTaken;
    }
    protected static int addStackToAmount(ItemStack itemStack,
                                 double amount,
                                 double amountTaken,
                                 int denomination,
                                 ArrayList<ItemStack> removeThese) {
        int stackAmount = itemStack.getAmount() * denomination;
        if (amount - amountTaken >= stackAmount) {
            removeThese.add(itemStack);
            return stackAmount;
        } else {
            int amountAdded = (int) Math.floor(amount - amountTaken);
            int newStackAmount = itemStack.getAmount() - (int) Math.ceil((double) amountAdded / denomination);
            itemStack.setAmount(newStackAmount);
            return amountAdded;
        }
    }
}
