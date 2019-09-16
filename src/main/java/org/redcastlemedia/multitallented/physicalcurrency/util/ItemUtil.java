package org.redcastlemedia.multitallented.physicalcurrency.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
}
