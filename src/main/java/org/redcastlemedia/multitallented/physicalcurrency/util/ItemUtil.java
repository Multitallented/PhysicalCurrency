package org.redcastlemedia.multitallented.physicalcurrency.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.redcastlemedia.multitallented.physicalcurrency.ConfigManager;

public final class ItemUtil {
    private ItemUtil() {

    }
    public static ItemStack processItem(String materialString,
                                        String displayName,
                                        List<String> lore) {
        ItemStack itemStack = new ItemStack(Material.valueOf(materialString));
        if (displayName != null || (lore != null && !lore.isEmpty())) {
            ItemMeta im = itemStack.getItemMeta();
            if (displayName != null) {
                im.setDisplayName(displayName);
            }
            if (lore != null && !lore.isEmpty()) {
                im.setLore(lore);
            }
            itemStack.setItemMeta(im);
        }
        return itemStack;
    }

    public static double countCurrencyInInventory(Inventory inventory) {
        ItemStack singleItem = ConfigManager.getInstance().getSingleItem();
        ItemStack nineItem = ConfigManager.getInstance().getNineItem();
        ItemStack eightOneItem = ConfigManager.getInstance().getEightyOneItem();

        double runningTotal = 0;
        for (ItemStack itemStack : inventory) {
            if (itemStack == null || (itemStack.getType() != singleItem.getType() &&
                    itemStack.getType() != nineItem.getType() &&
                    itemStack.getType() != eightOneItem.getType())) {
                continue;
            }
            if (isEquivalentItem(itemStack, singleItem)) {
                runningTotal += itemStack.getAmount();
            } else if (isEquivalentItem(itemStack, nineItem)) {
                runningTotal += itemStack.getAmount() * 9;
            } else if (isEquivalentItem(itemStack, eightOneItem)) {
                runningTotal += itemStack.getAmount() * 81;
            }
        }
        return runningTotal;
    }

    public static boolean isEquivalentItem(ItemStack stack1, ItemStack stack2) {
        if (stack1.getType() != stack2.getType()) {
            return false;
        }
        if (stack2.getItemMeta() == null) {
            return true;
        }
        if (stack1.getItemMeta() == null) {
            return false;
        }
        if (!stack1.getItemMeta().getDisplayName().equals(stack2.getItemMeta().getDisplayName())) {
            return false;
        }
        if (stack2.getItemMeta().getLore() == null || stack2.getItemMeta().getLore().isEmpty()) {
            return true;
        }
        if (stack1.getItemMeta().getLore() == null || stack1.getItemMeta().getLore().isEmpty()) {
            return false;
        }
        if (stack1.getItemMeta().getLore().size() != stack2.getItemMeta().getLore().size()) {
            return false;
        }
        int i = 0;
        for (String s : stack2.getItemMeta().getLore()) {
            if (!s.equals(stack1.getItemMeta().getLore().get(i))) {
                return false;
            }
            i++;
        }
        return true;
    }
}
